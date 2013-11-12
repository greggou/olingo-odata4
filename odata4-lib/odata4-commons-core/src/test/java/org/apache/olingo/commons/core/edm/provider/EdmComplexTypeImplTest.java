package org.apache.olingo.commons.core.edm.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmComplexType;
import org.apache.olingo.commons.api.edm.EdmElement;
import org.apache.olingo.commons.api.edm.helper.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.ComplexType;
import org.apache.olingo.commons.api.edm.provider.EdmProvider;
import org.apache.olingo.commons.api.edm.provider.NavigationProperty;
import org.apache.olingo.commons.api.edm.provider.Property;
import org.junit.Before;
import org.junit.Test;

public class EdmComplexTypeImplTest {

  private EdmComplexType baseType;
  private EdmComplexType type;

  @Before
  public void setupTypes() throws Exception {
    EdmProvider provider = mock(EdmProvider.class);
    EdmProviderImpl edm = new EdmProviderImpl(provider);

    FullQualifiedName baseName = new FullQualifiedName("namespace", "BaseTypeName");
    ComplexType baseComplexType = new ComplexType();
    List<Property> baseProperties = new ArrayList<Property>();
    baseProperties.add(new Property().setName("prop1"));
    List<NavigationProperty> baseNavigationProperties = new ArrayList<NavigationProperty>();
    baseNavigationProperties.add(new NavigationProperty().setName("nav1"));
    baseComplexType.setName("BaseTypeName").setAbstract(false).setOpenType(false).setProperties(baseProperties)
        .setNavigationProperties(baseNavigationProperties);
    when(provider.getComplexType(baseName)).thenReturn(baseComplexType);

    baseType = new EdmComplexTypeImpl(edm, baseName, baseComplexType);

    FullQualifiedName name = new FullQualifiedName("namespace", "typeName");
    ComplexType complexType = new ComplexType().setBaseType(baseName);
    List<Property> properties = new ArrayList<Property>();
    properties.add(new Property().setName("prop2"));
    List<NavigationProperty> navigationProperties = new ArrayList<NavigationProperty>();
    navigationProperties.add(new NavigationProperty().setName("nav2"));
    complexType.setName("BaseTypeName").setAbstract(false).setOpenType(false).setProperties(properties)
        .setNavigationProperties(navigationProperties);
    when(provider.getComplexType(name)).thenReturn(complexType);

    type = new EdmComplexTypeImpl(edm, name, complexType);
  }

  @Test
  public void getBaseType() {
    assertNull(baseType.getBaseType());
    assertNotNull(type.getBaseType());
  }

  @Test
  public void propertiesBehaviour() {
    List<String> propertyNames = baseType.getPropertyNames();
    assertEquals(1, propertyNames.size());
    assertEquals("prop1", baseType.getProperty("prop1").getName());
  }

  @Test
  public void propertiesBehaviourWithBaseType() {
    List<String> propertyNames = type.getPropertyNames();
    assertEquals(2, propertyNames.size());
    assertEquals("prop1", type.getProperty("prop1").getName());
    assertEquals("prop2", type.getProperty("prop2").getName());
  }

  @Test
  public void navigationPropertiesBehaviour() {
    List<String> navigationPropertyNames = baseType.getNavigationPropertyNames();
    assertEquals(1, navigationPropertyNames.size());
    assertEquals("nav1", baseType.getProperty("nav1").getName());
  }

  @Test
  public void navigationPropertiesBehaviourWithBaseType() {
    List<String> navigationPropertyNames = type.getNavigationPropertyNames();
    assertEquals(2, navigationPropertyNames.size());
    assertEquals("nav1", type.getProperty("nav1").getName());
    assertEquals("nav2", type.getProperty("nav2").getName());
  }

  @Test
  public void propertyCaching() {
    EdmElement property = type.getProperty("prop1");
    assertTrue(property == type.getProperty("prop1"));

    property = type.getProperty("prop2");
    assertTrue(property == type.getProperty("prop2"));

    property = type.getProperty("nav1");
    assertTrue(property == type.getProperty("nav1"));

    property = type.getProperty("nav2");
    assertTrue(property == type.getProperty("nav2"));
  }

}