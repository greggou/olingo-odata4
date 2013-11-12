package org.apache.olingo.commons.core.edm.primitivetype;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.olingo.commons.api.edm.EdmEnumType;
import org.apache.olingo.commons.api.edm.EdmMember;
import org.apache.olingo.commons.api.edm.constants.EdmTypeKind;
import org.junit.Test;

public class EdmEnumTest extends PrimitiveTypeBaseTest {

  private final EdmEnumType instance;

  public EdmEnumTest() {
    EdmMember member1 = mock(EdmMember.class);
    when(member1.getName()).thenReturn("first");
    when(member1.getValue()).thenReturn("1");
    EdmMember member2 = mock(EdmMember.class);
    when(member2.getName()).thenReturn("second");
    when(member2.getValue()).thenReturn("64");
    instance = new EdmEnum("namespace", "name",
        EdmPrimitiveTypeKind.SByte.getEdmPrimitiveTypeInstance(),
        Arrays.asList(member1, member2),
        true);
  }

  @Test
  public void nameSpace() throws Exception {
    assertEquals("namespace", instance.getNamespace());
  }

  @Test
  public void name() throws Exception {
    assertEquals("name", instance.getName());
  }

  @Test
  public void kind() throws Exception {
    assertEquals(EdmTypeKind.ENUM, instance.getKind());
  }

  @Test
  public void compatibility() {
    assertTrue(instance.isCompatible(instance));
    assertFalse(instance.isCompatible(instance.getUnderlyingType()));
  }

  @Test
  public void defaultType() throws Exception {
    assertEquals(Byte.class, instance.getDefaultType());
  }

  @Test
  public void members() throws Exception {
    assertArrayEquals(new String[] { "first", "second" }, instance.getMemberNames().toArray());
    assertEquals("64", instance.getMember("second").getValue());
    assertNull(instance.getMember("notExisting"));
  }

  @Test
  public void underlyingType() throws Exception {
    assertEquals(EdmPrimitiveTypeKind.SByte.getEdmPrimitiveTypeInstance(), instance.getUnderlyingType());
  }

  @Test
  public void validate() throws Exception {
    assertTrue(instance.validate(null, null, null, null, null, null));
    assertTrue(instance.validate(null, true, null, null, null, null));
    assertFalse(instance.validate(null, false, null, null, null, null));
    assertFalse(instance.validate("", null, null, null, null, null));
    assertFalse(instance.validate("something", null, null, null, null, null));

    assertTrue(instance.validate("second", null, null, null, null, null));
    assertTrue(instance.validate("first,second", null, null, null, null, null));
    assertTrue(instance.validate("64", null, null, null, null, null));
    assertTrue(instance.validate("1,64", null, null, null, null, null));
  }

  @Test
  public void toUriLiteral() throws Exception {
    assertNull(instance.toUriLiteral(null));
    assertEquals("namespace.name'first'", instance.toUriLiteral("first"));
  }

  @Test
  public void fromUriLiteral() throws Exception {
    assertNull(instance.fromUriLiteral(null));
    assertEquals("first", instance.fromUriLiteral("namespace.name'first'"));

    expectErrorInFromUriLiteral(instance, "");
    expectErrorInFromUriLiteral(instance, "name'first'");
    expectErrorInFromUriLiteral(instance, "namespace.name'first");
    expectErrorInFromUriLiteral(instance, "namespace.namespace'first");
  }

  @Test
  public void valueToString() throws Exception {
    assertNull(instance.valueToString(null, null, null, null, null, null));
    assertNull(instance.valueToString(null, true, null, null, null, null));
    assertEquals("first", instance.valueToString(1, null, null, null, null, null));
    assertEquals("first", instance.valueToString((byte) 1, null, null, null, null, null));
    assertEquals("first", instance.valueToString((short) 1, null, null, null, null, null));
    assertEquals("second", instance.valueToString(Integer.valueOf(64), null, null, null, null, null));
    assertEquals("second", instance.valueToString(64L, null, null, null, null, null));
    assertEquals("first,second", instance.valueToString(65, null, null, null, null, null));

    expectNullErrorInValueToString(instance);
    expectContentErrorInValueToString(instance, 3);
    expectTypeErrorInValueToString(instance, 1.0);
  }

  @Test
  public void valueOfString() throws Exception {
    assertNull(instance.valueOfString(null, null, null, null, null, null, Byte.class));
    assertNull(instance.valueOfString(null, true, null, null, null, null, Byte.class));
    assertEquals(Short.valueOf((short) 1), instance.valueOfString("1", null, null, null, null, null, Short.class));
    assertEquals(Integer.valueOf(1), instance.valueOfString("1", null, null, null, null, null, Integer.class));
    assertEquals(Long.valueOf(64L), instance.valueOfString("64", null, null, null, null, null, Long.class));
    assertEquals(Long.valueOf(1), instance.valueOfString("first", null, null, null, null, null, Long.class));
    assertEquals(Byte.valueOf((byte) 65), instance.valueOfString("first,64", null, null, null, null, null, Byte.class));
    assertEquals(Integer.valueOf(1), instance.valueOfString("1,1,first", null, null, null, null, null, Integer.class));

    final EdmEnumType nonFlagsInstance = new EdmEnum("namespace", "name",
        instance.getUnderlyingType(),
        Arrays.asList(instance.getMember("first"), instance.getMember("second")),
        false);
    assertEquals(Integer.valueOf(1), nonFlagsInstance.valueOfString("1", null, null, null, null, null, Integer.class));
    expectContentErrorInValueOfString(nonFlagsInstance, "1,64");

    expectNullErrorInValueOfString(instance);
    expectContentErrorInValueOfString(instance, "2");
    expectContentErrorInValueOfString(instance, "1,");
    expectContentErrorInValueOfString(instance, ",1");
    expectTypeErrorInValueOfString(instance, "1");
  }
}