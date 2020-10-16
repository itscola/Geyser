/*
 * Copyright (c) 2019-2020 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.connector.inventory;

import com.github.steveice10.mc.protocol.data.game.window.WindowType;
import lombok.Getter;
import lombok.NonNull;
import org.geysermc.connector.network.translators.inventory.InventoryTranslator;

/**
 * Combination of {@link Inventory} and {@link PlayerInventory}
 */
@Getter
public class Container extends Inventory {
    private final PlayerInventory playerInventory;
    private final int containerSize;

    public Container(String title, int id, WindowType windowType, int size, PlayerInventory playerInventory) {
        super(title, id, windowType, size);
        this.playerInventory = playerInventory;
        this.containerSize = this.size + InventoryTranslator.PLAYER_INVENTORY_SIZE;
    }

    @Override
    public GeyserItemStack getItem(int slot) {
        if (slot < this.size) {
            return super.getItem(slot);
        } else {
            return playerInventory.getItem(slot - this.size + InventoryTranslator.PLAYER_INVENTORY_OFFSET);
        }
    }

    @Override
    public void setItem(int slot, @NonNull GeyserItemStack item) {
        if (slot < this.size) {
            super.setItem(slot, item);
        } else {
            playerInventory.setItem(slot - this.size + InventoryTranslator.PLAYER_INVENTORY_OFFSET, item);
        }
    }

    @Override
    public int getSize() {
        return this.containerSize;
    }
}
