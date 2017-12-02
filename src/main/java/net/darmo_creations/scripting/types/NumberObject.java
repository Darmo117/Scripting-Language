/*
 * Copyright © 2017 Damien Vergnet
 * 
 * This file is part of Scripting Language.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.darmo_creations.scripting.types;

/**
 * Type for numbers.
 *
 * @author Damien Vergnet
 */
public final class NumberObject extends Value {
  public static final NumberObject ZERO = new NumberObject(0);
  public static final NumberObject ONE = new NumberObject(1);
  public static final NumberObject TWO = new NumberObject(2);
  public static final NumberObject THREE = new NumberObject(3);
  public static final NumberObject FOUR = new NumberObject(4);

  private final double value;

  public NumberObject(double value) {
    super("Number");
    this.value = value;
  }

  public double getValue() {
    return this.value;
  }

  @Override
  protected String str() {
    return "" + this.value;
  }
}
