#!/usr/bin/perl
#
# Copyright 2009 The Sakai Foundation
#
# Licensed under the Educational Community License, Version 1.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.opensource.org/licenses/ecl1.php
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#===============================================================================
#
#         FILE:  normalise_read_messages.pl
#
#        USAGE:  ./normalise_read_messages.pl 
#
#  DESCRIPTION:  Resets the values in YAFT_FORUM_READ and YAFT_DISCUSSION_READ
#
#      OPTIONS:  ---
# REQUIREMENTS:  ---
#         BUGS:  ---
#        NOTES:  ---
#       AUTHOR:   (), <>
#      COMPANY:  
#      VERSION:  1.0
#      CREATED:  03/04/09 11:18:46 GMT
#     REVISION:  ---
#===============================================================================

use strict;
use warnings;

use DBI;
use Data::Dumper;

use Getopt::Long;

my $dbname = undef;
my $dbuser = undef;
my $dbpass = undef;

GetOptions("dbname=s" => \$dbname,"dbuser=s" => \$dbuser,"dbpass=s" => \$dbpass);

unless(defined($dbname) && defined($dbuser) && defined($dbpass))
{
	print "Usage: normalise_read_messages.pl --dbname <DBNAME> --dbuser <DBUSER> --dbpass <DBPASS>\n";
	exit 0;
}

my $dbh = DBI->connect("DBI:mysql:database=$dbname",$dbuser,$dbpass,{RaiseError => 1,AutoCommit => 1})
	or die "Failed to connect to to database";

# This should get a record containing the reader's user id and the discussion_id, for every read messsage
my $rows = $dbh->selectall_arrayref(
	'SELECT USER_ID,DISCUSSION_ID FROM YAFT_MESSAGE,YAFT_READ_MESSAGES WHERE YAFT_MESSAGE.MESSAGE_ID = YAFT_READ_MESSAGES.MESSAGE_ID');


my $discussion_counts = {};
my $forum_counts = {};

$dbh->do("DELETE FROM YAFT_DISCUSSION_READ");
$dbh->do("DELETE FROM YAFT_FORUM_READ");

foreach my $row (@$rows)
{
	my $user = $row->[0];
	my $discussion = $row->[1];

	$discussion_counts->{$user}->{$discussion} += 1;
	print "Value: $discussion_counts->{$user}->{$discussion}\n";

	my @forum_row = $dbh->selectrow_array("SELECT FORUM_ID FROM YAFT_FORUM_DISCUSSION WHERE DISCUSSION_ID = '$discussion'");
	my $forum = $forum_row[0];

	$forum_counts->{$user}->{$forum} += 1;
	print "Value: $forum_counts->{$user}->{$forum}\n";
}


my $discussionsth = $dbh->prepare("INSERT INTO YAFT_DISCUSSION_READ (DISCUSSION_ID,USER_ID,NUMBER_READ) VALUES(?,?,?)");

foreach my $user (keys(%$discussion_counts))
{
	my $discussions = $discussion_counts->{$user};

	foreach my $discussion (keys(%$discussions))
	{
		my $count = $discussion_counts->{$user}->{$discussion};
		$discussionsth->execute($discussion,$user,$count) or die $dbh->errstr;
	}
}

my $forumsth = $dbh->prepare("INSERT INTO YAFT_FORUM_READ (FORUM_ID,USER_ID,NUMBER_READ) VALUES(?,?,?)");

foreach my $user (keys(%$forum_counts))
{
	my $forums = $forum_counts->{$user};

	foreach my $forum (keys(%$forums))
	{
		my $count = $forum_counts->{$user}->{$forum};
		$forumsth->execute($forum,$user,$count) or die $dbh->errstr;
	}
}

$dbh->disconnect();
