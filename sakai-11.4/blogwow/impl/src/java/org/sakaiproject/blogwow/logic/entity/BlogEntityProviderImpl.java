/******************************************************************************
 * BlogEntryEntityProviderImpl.java - created by aaronz on Jun 21, 2007
 * 
 * Copyright (c) 2007 Centre for Academic Research in Educational Technologies
 * Licensed under the Educational Community License version 1.0
 * 
 * A copy of the Educational Community License has been included in this 
 * distribution and is available at: http://www.opensource.org/licenses/ecl1.php
 * 
 * Contributors:
 * Aaron Zeckoski (aaronz@vt.edu) - primary
 * 
 *****************************************************************************/

package org.sakaiproject.blogwow.logic.entity;

import java.util.List;
import java.util.Map;

import org.sakaiproject.blogwow.logic.BlogLogic;
import org.sakaiproject.blogwow.model.BlogWowBlog;
import org.sakaiproject.entitybroker.DeveloperHelperService;
import org.sakaiproject.entitybroker.EntityReference;
import org.sakaiproject.entitybroker.entityprovider.CoreEntityProvider;
import org.sakaiproject.entitybroker.entityprovider.capabilities.AutoRegisterEntityProvider;
import org.sakaiproject.entitybroker.entityprovider.capabilities.CollectionResolvable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Createable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Inputable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Outputable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Resolvable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Saveable;
import org.sakaiproject.entitybroker.entityprovider.capabilities.Updateable;
import org.sakaiproject.entitybroker.entityprovider.extension.Formats;
import org.sakaiproject.entitybroker.entityprovider.search.Restriction;
import org.sakaiproject.entitybroker.entityprovider.search.Search;

/**
 * Implementation of blog entity provider,
 * provides access to the blog itself
 * 
 * @author Aaron Zeckoski (aaronz@vt.edu)
 */
public class BlogEntityProviderImpl implements BlogEntityProvider, CoreEntityProvider, 
      Saveable, Createable, Resolvable, Updateable, 
      CollectionResolvable, Outputable, Inputable, AutoRegisterEntityProvider {

   private BlogLogic blogLogic;
   public void setBlogLogic(BlogLogic blogLogic) {
      this.blogLogic = blogLogic;
   }

   private DeveloperHelperService developerHelperService;
   public void setDeveloperHelperService(DeveloperHelperService developerHelperService) {
      this.developerHelperService = developerHelperService;
   }

   /* (non-Javadoc)
    * @see org.sakaiproject.entitybroker.entityprovider.EntityProvider#getEntityPrefix()
    */
   public String getEntityPrefix() {
      return ENTITY_PREFIX;
   }

   public boolean entityExists(String id) {
      // entity is real if there are any entries that match this id
      String blogId = id;
      if (blogLogic.getBlogById(blogId) != null) {
         return true;
      }
      return false;
   }

   // Added for compatibility
   public String createEntity(EntityReference ref, Object entity, Map<String, Object> params) {
       return createEntity(ref, entity);
   }

   public void updateEntity(EntityReference ref, Object entity, Map<String, Object> params) {
       updateEntity(ref, entity);
   }

   public String createEntity(EntityReference ref, Object entity) {
      BlogWowBlog incoming = (BlogWowBlog) entity;
      if (incoming.getLocation() == null) {
         throw new IllegalArgumentException("The blog locationId must be set to create a blog, " +
         		"you can get this from the DeveloperHelperService");
      }
      if (incoming.getOwnerId() == null) {
         throw new IllegalArgumentException("The blog ownerId must be set to create a blog");
      }
      BlogWowBlog blog = blogLogic.makeBlogByLocationAndUser(incoming.getLocation(), incoming.getOwnerId());
      // copy over values
      developerHelperService.copyBean(incoming, blog, 0, new String[] {"locationId","ownerId","dateCreated"}, true);
      blogLogic.saveBlog(blog, blog.getLocation());
      return blog.getId();
   }

   public Object getSampleEntity() {
      return new BlogWowBlog();
   }

   public void updateEntity(EntityReference ref, Object entity) {
      String blogId = ref.getId();
      if (blogId == null) {
         throw new IllegalArgumentException("The id must be set when updating a blog");
      }
      BlogWowBlog blog = blogLogic.getBlogById(blogId);
      if (blog == null) {
         throw new IllegalArgumentException("Cannot find a blog to update with this reference: " + ref);
      }
      BlogWowBlog incoming = (BlogWowBlog) entity;
      // copy over values
      developerHelperService.copyBean(incoming, blog, 0, new String[] {"locationId","ownerId","dateCreated"}, true);
      blogLogic.saveBlog(blog, blog.getLocation());
   }

   public Object getEntity(EntityReference ref) {
      String blogId = ref.getId();
      if (blogId == null) {
         return new BlogWowBlog();
      }
      BlogWowBlog blog = blogLogic.getBlogById(blogId);
      if (blog == null) {
         throw new IllegalArgumentException("No blog found with this id: " + blogId);
      }
      return blog;
   }

   public List<?> getEntities(EntityReference ref, Search search) {
      String locationId = developerHelperService.getCurrentLocationReference();
      if (search != null && !search.isEmpty()) {
         Restriction restriction = search.getRestrictionByProperty("location");
         locationId = (String) restriction.getSingleValue();
      }
      List<BlogWowBlog> blogs = blogLogic.getAllVisibleBlogs(locationId, null, true, 0, 50);
      return blogs;
   }

   public String[] getHandledOutputFormats() {
      return new String[] { Formats.XML, Formats.JSON };
   }

   public String[] getHandledInputFormats() {
      return new String[] { Formats.HTML, Formats.XML, Formats.JSON };
   }

}
