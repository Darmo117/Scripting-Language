package net.darmo_creations.scripting.types;

import java.util.Objects;

public abstract class Value {
  private final String name;

  public Value(String name) {
    this.name = Objects.requireNonNull(name);
  }

  public String getName() {
    return this.name;
  }

  @Override
  public final String toString() {
    return str();
  }

  /**
   * Returns the string representation of this structure.
   */
  protected abstract String str();
}
