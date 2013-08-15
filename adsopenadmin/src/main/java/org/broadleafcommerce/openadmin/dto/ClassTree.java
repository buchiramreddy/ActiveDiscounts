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

package org.broadleafcommerce.openadmin.dto;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ClassTree implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected ClassTree[] children                = new ClassTree[0];

  /** DOCUMENT ME! */
  protected boolean     excludeFromPolymorphism;

  /** DOCUMENT ME! */
  protected String      friendlyName;

  /** DOCUMENT ME! */
  protected String fullyQualifiedClassname;

  /** DOCUMENT ME! */
  protected int    left;

  /** DOCUMENT ME! */
  protected String name;

  /** DOCUMENT ME! */
  protected int    right;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ClassTree object.
   */
  public ClassTree() {
    // do nothing
  }

  /**
   * Creates a new ClassTree object.
   *
   * @param  fullyQualifiedClassname  DOCUMENT ME!
   */
  public ClassTree(String fullyQualifiedClassname) {
    this(fullyQualifiedClassname, null, false);
  }

  /**
   * Creates a new ClassTree object.
   *
   * @param  fullyQualifiedClassname  DOCUMENT ME!
   * @param  excludeFromPolymorphism  DOCUMENT ME!
   */
  public ClassTree(String fullyQualifiedClassname, boolean excludeFromPolymorphism) {
    this(fullyQualifiedClassname, null, excludeFromPolymorphism);
  }

  /**
   * Creates a new ClassTree object.
   *
   * @param  fullyQualifiedClassname  DOCUMENT ME!
   * @param  friendlyName             DOCUMENT ME!
   * @param  excludeFromPolymorphism  DOCUMENT ME!
   */
  public ClassTree(String fullyQualifiedClassname, String friendlyName, boolean excludeFromPolymorphism) {
    setFullyQualifiedClassname(fullyQualifiedClassname);
    this.friendlyName            = friendlyName;
    this.excludeFromPolymorphism = excludeFromPolymorphism;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   start  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int finalizeStructure(int start) {
    left = start;
    start++;

    for (int i = children.length - 1; i >= 0; i--) {
      start = children[i].finalizeStructure(start);
      start++;
    }

    right = start;

    return start;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fullyQualifiedClassname  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ClassTree find(String fullyQualifiedClassname) {
    if (this.fullyQualifiedClassname.equals(fullyQualifiedClassname)) {
      return this;
    }

    ClassTree result = null;

    for (ClassTree child : children) {
      result = child.find(fullyQualifiedClassname);

      if (result != null) {
        break;
      }
    }

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ClassTree[] getChildren() {
    return children;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<ClassTree> getCollapsedClassTrees() {
    List<ClassTree> list = new ArrayList<ClassTree>();
    addChildren(this, list);

    return list;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFriendlyName() {
    return (friendlyName == null) ? name : friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFullyQualifiedClassname() {
    return fullyQualifiedClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getLeft() {
    return left;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getRight() {
    return right;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean hasChildren() {
    return children.length > 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isExcludeFromPolymorphism() {
    return this.excludeFromPolymorphism;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  children  DOCUMENT ME!
   */
  public void setChildren(ClassTree[] children) {
    this.children = children;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  friendlyName  DOCUMENT ME!
   */
  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fullyQualifiedClassname  DOCUMENT ME!
   */
  public void setFullyQualifiedClassname(String fullyQualifiedClassname) {
    this.fullyQualifiedClassname = fullyQualifiedClassname;

    int pos = fullyQualifiedClassname.lastIndexOf('.');

    if (pos >= 0) {
      name = fullyQualifiedClassname.substring(pos + 1, fullyQualifiedClassname.length());
    } else {
      name = fullyQualifiedClassname;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  left  DOCUMENT ME!
   */
  public void setLeft(int left) {
    this.left = left;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  right  DOCUMENT ME!
   */
  public void setRight(int right) {
    this.right = right;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  tree  DOCUMENT ME!
   * @param  list  DOCUMENT ME!
   */
  protected void addChildren(ClassTree tree, List<ClassTree> list) {
    if (!tree.isExcludeFromPolymorphism()) {
      list.add(tree);
    }

    for (ClassTree child : tree.getChildren()) {
      addChildren(child, list);
    }
  }
} // end class ClassTree
