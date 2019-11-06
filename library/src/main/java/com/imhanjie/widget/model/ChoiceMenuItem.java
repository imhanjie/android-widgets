package com.imhanjie.widget.model;

import java.util.Objects;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-08-14
 */

public class ChoiceMenuItem<T> {

    private boolean selected = false;

    private String name;

    private T value;

    public ChoiceMenuItem(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public ChoiceMenuItem(T value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this.value == null) {
            return false;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChoiceMenuItem<?> that = (ChoiceMenuItem<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
