package com.smartprogrammingbaddies.storagecenter;

import com.google.gson.JsonObject;
import com.smartprogrammingbaddies.item.Item;
import com.smartprogrammingbaddies.logger.Transaction;
import com.smartprogrammingbaddies.organization.Organization;
import com.smartprogrammingbaddies.utils.TimeSlot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The StorageCenter class is used to store and manage goods such
 * as foods, toiletries, or clothes. It provides methods to add,
 * remove, and list items in the storage.
 */
@Entity
public class StorageCenter {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "storage_center_id")
  private int id;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private String name;
  @OneToOne(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
  private Organization organization;
  @ElementCollection
  @CollectionTable(name = "operation_hours", joinColumns = @JoinColumn(name = "storage_center_id"))
  @MapKeyColumn(name = "day_of_week")
  @Column(name = "hours_of_operation")
  private Map<DayOfWeek, TimeSlot> operationHours = new EnumMap<>(DayOfWeek.class);
  @OneToMany(mappedBy = "storageCenter", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Item> items;
  @OneToMany(mappedBy = "storageCenter", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Transaction> transactions;

  /**
   * Constructs a new StorageCenter where donated items can be tracked.
   * The storage center will contain a set of Item objects. Initially,
   * the set is empty.
   *
   * @param name the name of the storage center
   * @param description the description of the storage center
   * @throws IllegalArgumentException if the name is null, empty, or blank
   */
  public StorageCenter(String name, String description) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Storage center name cannot be null, or blank.");
    }
    this.name = name;

    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Storage center description cannot be null, or blank.");
    }
    this.description = description;
    this.items = new HashSet<Item>();
  }

  /**
   * Empty constructor needed for JPA.
   */
  public StorageCenter() {
    // Empty constructor needed for JPA
  }

  /**
   * Gets the database ID of the storage center.
   *
   * @return the database ID of the storage center
   */
  public int getDatabaseId() {
    return id;
  }

  /**
   * Sets the database ID of the storage center.
   *
   * @param id the new database ID of the storage center
   */
  public void setDatabaseId(int id) {
    this.id = id;
  }

  /**
   * Lists all items in the storage.
   *
   * @return a Set of all items in the storage
   */
  public Set<Item> getItems() {
    return items;
  }

  /**
   * Sets the items in the storage center.
   *
   * @param items the new set of items in the storage center
   */
  public void setItems(Set<Item> items) {
    this.items = items;
  }

  /**
   * Returns the name of the storage center.
   *
   * @return the name of the storage center
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the storage center.
   *
   * @param name the new name of the storage center
   * @throws IllegalArgumentException if the name is null, empty, or blank
   */
  public void changeName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Storage center name cannot be null, or blank.");

    }
    this.name = name;
  }

  /**
   * Returns the description of the storage center.
   *
   * @return the description of the storage center
   */
  public String getDescription() {
    return description;
  }

  /**
   * gets the organization associated with the storage center.
   *
   * @return the organization associated with the storage center
   */
  public Organization getOrganization() {
    return organization;
  }

  /**
   * gets the organization associated with the storage center.
   *
   * @param organization the organization to associate this storage center with
   */
  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  /**
   * Sets the description of the storage center.
   *
   * @param description the new description of the storage center
   * @throws IllegalArgumentException if the description is null, empty, or blank
   */
  public void changeDescription(String description) {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Storage center description cannot be null, or blank.");
    }
    this.description = description;
  }

  /**
   * Returns the operating hours of the storage center.
   *
   * @return Map of operating hours of the storage center
   */
  public Map<DayOfWeek, TimeSlot> getOperatingHours() {
    return operationHours;
  }

  /**
   * Sets the operating hours of the storage center.
   *
   * @param timeSlot the hours of operation for the storage center
   * @param day int of the day of the week between 1 and 7
   * @throws IllegalArgumentException if the day is not between 1 and 7
   */
  public void updateDayHours(TimeSlot timeSlot, int day) {
    if (day < 1 || day > 7) {
      throw new IllegalArgumentException("Day of the week must be between 1 and 7.");
    }
    operationHours.put(DayOfWeek.of(day), timeSlot);
  }

  /**
   * get all expired Items in the storage center.
   *
   * @return a set of all expired items in the storage center
   */
  public Set<Item> getExpiredItems() {
    Set<Item> expiredItems = new HashSet<>();
    for (Item item : items) {
      if (item.isExpired()) {
        expiredItems.add(item);
      }
    }
    return expiredItems;
  }

  /**
   * Remove expired items from the storage.
   */
  public void removeExpiredItems() {
    Set<Item> expiredItems = getExpiredItems();
    for (Item item : expiredItems) {
      transactions.add(new Transaction(this, item, item.getQuantity(), "Remove Expired Item"));
      items.remove(item);
    }
  }

  /**
   * Prints all the items in the storage.
   *
   * @return a string of all items in the storage
   */
  public String printItems() {
    StringBuilder result = new StringBuilder();
    result.append("Items: ").append("\n");
    for (Item item : items) {
      result.append(item.toString()).append("\n");
    }
    return result.toString();
  }

  /**
   * Gets the transactions that have occurred in the storage center.
   *
   * @return the transactions that have occurred in the storage center
   */
  public Set<Transaction> getTransactions() {
    return transactions;
  }

  /**
   * Sets the transactions that have occurred in the storage center.
   *
   * @param transactions the transactions that have occurred in the storage center
   */
  public void setTransactions(Set<Transaction> transactions) {
    if (transactions == null) {
      throw new IllegalArgumentException("Transactions must not be null.");
    }

    if (this.transactions != null) {
      throw new IllegalArgumentException("Transactions have already been set.");
    }

    this.transactions = transactions;
  }


  /**
   * Returns a string representation of the storage center, including its name,
   * description, and operating hours.
   *
   * @return a string representation of the storage center
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("Storage Center Name: ").append(name).append("\n");
    result.append("Description: ").append(description).append("\n");
    result.append("Operating Hours: ").append("\n");
    operationHours.forEach((day, hours) -> {
      result.append(day).append(": ");
      result.append(hours.toString()).append("\n");
    });
    return result.toString();
  }

  /**
   * Converts storage center info into json format.
   *
   * @return a json string of the storage center
   */
  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    json.addProperty("name", name);
    json.addProperty("description", description);
    JsonObject hours = new JsonObject();
    operationHours.forEach((day, timeSlot) -> {
      hours.add(day.toString(), timeSlot.toJson());
    });

    json.add("operationHours", hours);

    return json;
  }
}