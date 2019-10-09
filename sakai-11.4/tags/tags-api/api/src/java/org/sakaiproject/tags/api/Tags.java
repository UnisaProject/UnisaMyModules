/**********************************************************************************
 *
 * Copyright (c) 2016 The Sakai Foundation
 *
 * Original developers:
 *
 *   Unicon
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.tags.api;

import java.util.List;
import java.util.Optional;

/**
 * The interface for the tag sub-service.
 */
public interface Tags {

    public String createTag(Tag tag);

    public void updateTag(Tag tag);

    public void deleteTag(String tagId);

    public List<Tag> getAll();

    public Optional<Tag> getForId(String tagId);

    public List<Tag> getTagsPaginatedInCollection(int pageNum, int pageSize, String tagcollectionid);

    public int getTotalTagsInCollection(final String tagcollectionid);

    public int getTotalTagsByPrefixInLabel(final String label);

    public Optional<Tag> getForExternalIdAndCollection(String tagExternalId,String tagCollectionId);

    public List<Tag> getAllInCollection(String tagCollectionId);

    public List<Tag> getTagsByExactLabel(String label);

    public List<Tag> getTagsByPartialLabel(String label);

    public List<Tag> getTagsByPrefixInLabel(String label);

    public List<Tag> getTagsPaginatedByPrefixInLabel(int pageNum, int pageSize, String label);

    public List<String> deleteTagsOlderThanDateFromCollection(String tagCollectionId, long lastmodificationdate );

    public List<String> deleteTagFromExternalCollection(String externalId, String tagCollectionId );

}
