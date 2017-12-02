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
package net.darmo_creations.scripting.exceptions;

/**
 * This exception is thrown when a variable or function is defined more than once is the same scope.
 *
 * @author Damien Vergnet
 */
public class AlreadyDefinedException extends RuntimeException {
  private static final long serialVersionUID = 3270188702942307079L;

  public AlreadyDefinedException(String message) {
    super(message);
  }
}
