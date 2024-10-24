package com.smartprogrammingbaddies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Unit tests for the Event class.
 */
@Tag("local")
@SpringBootTest
@ContextConfiguration
public class EventUnitTests {

  /**
   *  The Volunteer set up to be tested.
   */
  @BeforeEach
  public void setupEventForTesting() {
    testOrganizer = new StorageCenter("Food Pantry");
    testVolunteerMap = new HashMap<>();
    
    Volunteer volunteer1 = new Volunteer("John Doe", "Cook", "10-17-2024", new HashMap<>());
    Volunteer volunteer2 = new Volunteer("Jane Smith", "Server", "10-18-2024", new HashMap<>());
    testVolunteerMap.put(volunteer1.getName(), volunteer1);
    testVolunteerMap.put(volunteer2.getName(), volunteer2);

    testEvent = new Event("Charity Drive", "A community charity event", "12-25-2024", "10:00 AM", 
                          "East Village", testOrganizer, testVolunteerMap);
  }

  /**
   * Tests the getName() method to verify the event name is correct.
   */
  @Test
  public void getNameTest() {
    String expectedName = "Charity Drive";
    assertEquals(expectedName, testEvent.getName());
  }

  /**
   * Tests the updateName() method to verify the event name is correct
   *  after being updated.
   */
  @Test
  public void updateNameTest() {
    testEvent.updateName("Food Drive");
    String expectedName = "Food Drive";
    assertEquals(expectedName, testEvent.getName());
  }

  /**
   * Tests the getDescription() method to verify the event description is correct.
   */
  @Test
  public void getDescriptionTest() {
    String expectedDescription = "A community charity event";
    assertEquals(expectedDescription, testEvent.getDescription());
  }
  
  /**
   * Tests the updateDescription() method to verify the event description is correct
   * after being updated.
   */
  @Test
  public void updateDescriptionTest() {
    testEvent.updateDescription("A community food event");
    String expectedDescription = "A community food event";
    assertEquals(expectedDescription, testEvent.getDescription());
  }

  /**
   * Tests the getDate() method to verify the event date is correct.
   */
  @Test
  public void getDateTest() {
    String expectedDate = "12-25-2024";
    assertEquals(expectedDate, testEvent.getDate());
  }

  /**
   * Tests the updateDate() method to verify the event date is correct
   * after being updated.
   */
  @Test
  public void updateDateTest() {
    testEvent.updateDate("12-26-2024");
    String expectedDate = "12-26-2024";
    assertEquals(expectedDate, testEvent.getDate());
  }

  /**
   * Tests the getTime() method to verify the event time is correct.
   */
  @Test
  public void getTimeTest() {
    String expectedTime = "10:00 AM";
    assertEquals(expectedTime, testEvent.getTime());
  }

  /**
   * Tests the updateTime() method to verify the event time is correct
   * after being updated.
   */
  @Test
  public void updatedTimeTest() {
    testEvent.updateTime("11:00 AM");
    String expectedTime = "11:00 AM";
    assertEquals(expectedTime, testEvent.getTime());
  }

  /**
   * Tests the getLocation() method to verify the event location is correct.
   */
  @Test
  public void getLocationTest() {
    String expectedLocation = "East Village";
    assertEquals(expectedLocation, testEvent.getLocation());
  }

  /**
   * Tests the updateLocation() method to verify the event location is correct
   * after being updated.
   */
  @Test
  public void updateLocationTest() {
    testEvent.updateLocation("West Village");
    String expectedLocation = "West Village";
    assertEquals(expectedLocation, testEvent.getLocation());
  }

  /**
   * Tests the getOrganizer() method to verify the event organizer is correct.
   */
  @Test
  public void getOrganizerTest() {
    assertEquals(testOrganizer, testEvent.getOrganizer());
  }

  /**
   * Tests the getListOfVolunteers() method to verify the list of volunteers is correct.
   */
  @Test
  public void getListOfVolunteersTest() {
    assertEquals(testVolunteerMap, testEvent.getListOfVolunteers());
  }

  /**
   * Tests the getVolunteerCount() method to verify the number of volunteers is correct.
   */
  @Test
  public void getVolunteerCountTest() {
    int expectedVolunteerCount = 2;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  /**
   * Tests the addVolunteer() method to verify a new volunteer can be added to the event.
   */
  @Test
  public void addVolunteerTest() {
    Volunteer newVolunteer = new Volunteer("Jason Johnson", "Server",
                               "10-19-2024", new HashMap<>());
    testEvent.addVolunteer(newVolunteer);
    int expectedVolunteerCount = 3;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  /**
   * Tests the removeVolunteer() method to verify a volunteer can be removed from the event.
   */
  @Test
  public void removeVolunteerTest() {
    testEvent.removeVolunteer("John Doe");
    int expectedVolunteerCount = 1;
    assertEquals(expectedVolunteerCount, testEvent.getVolunteerCount());
  }

  /**
   * Tests the toString() method to verify the string representation of the event is correct
   *  when the event has volunteers.
   */
  @Test
  public void toStringWithVoluneersTest() {
    String expectedString = "Event Name: Charity Drive\n"
                            + "Description: A community charity event\n"
                            + "Date: 12-25-2024\n"
                            + "Time: 10:00 AM\n"
                            + "Location: East Village\n"
                            + "Organizer: Food Pantry\n"
                            + "Volunteer Names: \n"
                            + "- John Doe\n"
                            + "- Jane Smith\n";

    assertEquals(expectedString, testEvent.toString());
  }

  /**
   * Tests the toString() method to verify the string representation of the event is correct
   *  when the event has no volunteers.
   */
  @Test
  public void toStringWithNoVoluneersTest() {
    testEvent.removeVolunteer("John Doe");
    testEvent.removeVolunteer("Jane Smith");
    String expectedString = "Event Name: Charity Drive\n"
                            + "Description: A community charity event\n"
                            + "Date: 12-25-2024\n"
                            + "Time: 10:00 AM\n"
                            + "Location: East Village\n"
                            + "Organizer: Food Pantry\n"
                            + "No volunteers signed up yet.\n";
    assertEquals(expectedString, testEvent.toString());
  }

  public static Event testEvent;
  public static StorageCenter testOrganizer;
  public static Map<String, Volunteer> testVolunteerMap;
}
