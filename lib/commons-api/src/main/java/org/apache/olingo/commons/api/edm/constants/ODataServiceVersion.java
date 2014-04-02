/* 
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.commons.api.edm.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a container for the supported ODataServiceVersions.
 */
public enum ODataServiceVersion {

  V10("1.0"),
  V20("2.0"),
  V30("3.0"),
  V40("4.0");

  private static final Pattern DATASERVICEVERSIONPATTERN = Pattern.compile("(\\p{Digit}+\\.\\p{Digit}+)(:?;.*)?");

  public static final String NS_DATASERVICES = "dataservices";

  public static final String NS_METADATA = "metadata";

  public static final String NS_SCHEME = "scheme";

  public static final String NAVIGATION_LINK_REL = "navigationLinkRel";

  public static final String ASSOCIATION_LINK_REL = "associationLinkRel";

  public static final String MEDIA_EDIT_LINK_REL = "mediaEditLinkRel";

  public static final String JSON_TYPE = "jsonType";

  public static final String JSON_ID = "jsonId";

  public static final String JSON_ETAG = "jsonETag";

  public static final String JSON_READ_LINK = "jsonReadLink";

  public static final String JSON_EDIT_LINK = "jsonEditLink";

  public static final String JSON_MEDIAREAD_LINK = "jsonMediaReadLink";

  public static final String JSON_MEDIAEDIT_LINK = "jsonMediaEditLink";

  public static final String JSON_MEDIA_CONTENT_TYPE = "jsonMediaContentType";

  public static final String JSON_MEDIA_ETAG = "jsonMediaETag";

  public static final String JSON_ASSOCIATION_LINK = "jsonAssociationLink";

  public static final String JSON_NAVIGATION_LINK = "jsonNavigationLink";
  public static final String JSON_ERROR = "jsonError";

  private static final Map<String, String> V30_NAMESPACES = Collections.unmodifiableMap(new HashMap<String, String>() {

    private static final long serialVersionUID = 3109256773218160485L;

    {
      put(NS_DATASERVICES, "http://schemas.microsoft.com/ado/2007/08/dataservices");
      put(NS_METADATA, "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata");
      put(NS_SCHEME, "http://schemas.microsoft.com/ado/2007/08/dataservices/scheme");
      put(NAVIGATION_LINK_REL, "http://schemas.microsoft.com/ado/2007/08/dataservices/related/");
      put(ASSOCIATION_LINK_REL, "http://schemas.microsoft.com/ado/2007/08/dataservices/relatedlinks/");
      put(MEDIA_EDIT_LINK_REL, "http://schemas.microsoft.com/ado/2007/08/dataservices/edit-media/");
    }
  });

  private static final Map<String, String> V30_JSON = Collections.unmodifiableMap(new HashMap<String, String>() {

    private static final long serialVersionUID = 3109256773218160485L;

    {
      put(JSON_TYPE, "odata.type");
      put(JSON_ID, "odata.id");
      put(JSON_ETAG, "odata.etag");
      put(JSON_READ_LINK, "odata.readLink");
      put(JSON_EDIT_LINK, "odata.editLink");
      put(JSON_MEDIAREAD_LINK, "odata.mediaReadLink");
      put(JSON_MEDIAEDIT_LINK, "odata.mediaEditLink");
      put(JSON_MEDIA_CONTENT_TYPE, "odata.mediaContentType");
      put(JSON_MEDIA_ETAG, "odata.mediaEtag");
      put(JSON_ASSOCIATION_LINK, "@odata.associationLinkUrl");
      put(JSON_NAVIGATION_LINK, "@odata.navigationLinkUrl");
      put(JSON_ERROR, "odata.error");
    }
  });

  private static final Map<String, String> V40_NAMESPACES = Collections.unmodifiableMap(new HashMap<String, String>() {

    private static final long serialVersionUID = 3109256773218160485L;

    {
      put(NS_METADATA, "http://docs.oasis-open.org/odata/ns/metadata");
      put(NS_DATASERVICES, "http://docs.oasis-open.org/odata/ns/data");
      put(NS_SCHEME, "http://docs.oasis-open.org/odata/ns/scheme");
      put(NAVIGATION_LINK_REL, "http://docs.oasis-open.org/odata/ns/related/");
      put(ASSOCIATION_LINK_REL, "http://docs.oasis-open.org/odata/ns/relatedlinks/");
      put(MEDIA_EDIT_LINK_REL, "http://docs.oasis-open.org/odata/ns/edit-media/");
    }
  });

  private static final Map<String, String> V40_JSON = Collections.unmodifiableMap(new HashMap<String, String>() {

    private static final long serialVersionUID = 3109256773218160485L;

    {
      put(JSON_TYPE, "@odata.type");
      put(JSON_ID, "@odata.id");
      put(JSON_ETAG, "@odata.etag");
      put(JSON_READ_LINK, "@odata.readLink");
      put(JSON_EDIT_LINK, "@odata.editLink");
      put(JSON_MEDIAREAD_LINK, "@odata.mediaReadLink");
      put(JSON_MEDIAEDIT_LINK, "@odata.mediaEditLink");
      put(JSON_MEDIA_CONTENT_TYPE, "@odata.mediaContentType");
      put(JSON_MEDIA_ETAG, "@odata.mediaEtag");
      put(JSON_ASSOCIATION_LINK, "@odata.associationLink");
      put(JSON_NAVIGATION_LINK, "@odata.navigationLink");
      put(JSON_ERROR, "error");
    }
  });

  /**
   * Validates format and range of a data service version string.
   *
   * @param version version string
   * @return <code>true</code> for a valid version
   */
  public static boolean validateDataServiceVersion(final String version) {
    final Matcher matcher = DATASERVICEVERSIONPATTERN.matcher(version);
    if (matcher.matches()) {
      final String possibleDataServiceVersion = matcher.group(1);
      return V10.toString().equals(possibleDataServiceVersion)
              || V20.toString().equals(possibleDataServiceVersion)
              || V30.toString().equals(possibleDataServiceVersion)
              || V40.toString().equals(possibleDataServiceVersion);
    } else {
      throw new IllegalArgumentException(version);
    }
  }

  /**
   * actual > comparedTo
   *
   * @param actual
   * @param comparedTo
   * @return <code>true</code> if actual is bigger than comparedTo
   */
  public static boolean isBiggerThan(final String actual, final String comparedTo) {
    if (!validateDataServiceVersion(comparedTo) || !validateDataServiceVersion(actual)) {
      throw new IllegalArgumentException("Illegal arguments: " + comparedTo + " and " + actual);
    }

    final double me = Double.parseDouble(extractDataServiceVersionString(actual));
    final double other = Double.parseDouble(extractDataServiceVersionString(comparedTo));

    return me > other;
  }

  private static String extractDataServiceVersionString(final String rawDataServiceVersion) {
    if (rawDataServiceVersion != null) {
      final String[] pattern = rawDataServiceVersion.split(";");
      return pattern[0];
    }

    return null;
  }
  private final String version;

  private ODataServiceVersion(final String version) {
    this.version = version;
  }

  public Map<String, String> getNamespaceMap() {
    return this == V10 || this == V20
            ? Collections.<String, String>emptyMap()
            : this == V30
            ? V30_NAMESPACES
            : V40_NAMESPACES;
  }

  public Map<String, String> getJSONMap() {
    return this == V10 || this == V20
            ? Collections.<String, String>emptyMap()
            : this == V30
            ? V30_JSON
            : V40_JSON;
  }

  @Override
  public String toString() {
    return version;
  }
}
