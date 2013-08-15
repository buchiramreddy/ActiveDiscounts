/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;
import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blSandBoxElements"
)
@EntityListeners(value = { AdminAuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SANDBOX_ACTION")
@javax.persistence.Entity public class SandBoxActionImpl implements SandBoxAction {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Embedded protected AdminAuditable auditable = new AdminAuditable();

  /** DOCUMENT ME! */
  @Column(name = "ACTION_COMMENT")
  protected String comment;

  /** DOCUMENT ME! */
  @Column(name = "SANDBOX_ACTION_ID")
  @GeneratedValue(generator = "SandBoxActionId")
  @GenericGenerator(
    name       = "SandBoxActionId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SandBoxActionImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.openadmin.server.domain.SandBoxActionImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "ACTION_TYPE")
  protected String sandBoxActionType;

  /** DOCUMENT ME! */
  @JoinTable(
    name               = "SANDBOX_ITEM_ACTION",
    joinColumns        = {
      @JoinColumn(
        name           = "SANDBOX_ACTION_ID",
        referencedColumnName = "SANDBOX_ACTION_ID"
      )
    },
    inverseJoinColumns = {
      @JoinColumn(
        name           = "SANDBOX_ITEM_ID",
        referencedColumnName = "SANDBOX_ITEM_ID"
      )
    }
  )
  @ManyToMany(
    targetEntity = SandBoxItemImpl.class,
    cascade      = CascadeType.ALL
  )
  protected List<SandBoxItem> sandBoxItems;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#addSandBoxItem(org.broadleafcommerce.openadmin.server.domain.SandBoxItem)
   */
  @Override public void addSandBoxItem(SandBoxItem item) {
    if (sandBoxItems == null) {
      sandBoxItems = new ArrayList<SandBoxItem>();
    }

    sandBoxItems.add(item);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#getActionType()
   */
  @Override public SandBoxActionType getActionType() {
    return SandBoxActionType.getInstance(sandBoxActionType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#getAuditable()
   */
  @Override public AdminAuditable getAuditable() {
    return auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#getComment()
   */
  @Override public String getComment() {
    return comment;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#getSandBoxItems()
   */
  @Override public List<SandBoxItem> getSandBoxItems() {
    return sandBoxItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#setActionType(org.broadleafcommerce.openadmin.server.domain.SandBoxActionType)
   */
  @Override public void setActionType(SandBoxActionType type) {
    sandBoxActionType = type.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#setAuditable(org.broadleafcommerce.openadmin.audit.AdminAuditable)
   */
  @Override public void setAuditable(AdminAuditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#setComment(java.lang.String)
   */
  @Override public void setComment(String comment) {
    this.comment = comment;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxAction#setSandBoxItems(java.util.List)
   */
  @Override public void setSandBoxItems(List<SandBoxItem> sandBoxItems) {
    this.sandBoxItems = sandBoxItems;
  }
} // end class SandBoxActionImpl
