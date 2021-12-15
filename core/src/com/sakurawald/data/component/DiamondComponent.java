package com.sakurawald.data.component;

import games.rednblack.editor.renderer.components.BaseComponent;

public class DiamondComponent implements BaseComponent {

    public int value = 1;

    @Override
    public void reset() {
        value = 1;
    }
}