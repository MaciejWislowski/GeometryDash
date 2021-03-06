package com.engine;


import com.dataStructure.Transform;
import com.file.Parser;
import com.file.Serialize;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameObject extends Serialize {

    private List<Component> components;
    public String name;
    private boolean serializable = true;
    public boolean isUI = false;
    public boolean isSelected = false;

    public Transform transform;

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.transform = transform;

        this.components = new ArrayList<>();
    }

    public <T extends  Component> T getComponent(Class<T> componentClass) {
        for(Component c : components) {
            if(componentClass.isAssignableFrom(c.getClass())){
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
        return null;
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    public void addComponent(Component c) {
        components.add(c);
        c.gameObject = this;
    }

    public <T extends  Component> void removeComponent(Class<T> componentClass) {
        for(Component c: components) {
            if(componentClass.isAssignableFrom(c.getClass())){
                components.remove(c);
                return;
            }
        }
    }

    public <T extends  Component> void setIsSelected(Class<T> componentClass) {
        for(Component c: components) {
            if(componentClass.isAssignableFrom(c.getClass())){
                components.remove(c);
                return;
            }
        }
    }

    public GameObject copy() {
        GameObject newGameObject = new GameObject("Generated", transform.copy());
        for (Component c: components) {
            Component copy = c.copy();
            if(c.copy() != null) {
                newGameObject.addComponent(copy);
            }
        }
        return newGameObject;
    }

    public void update(double dt) {
        for(Component c: components) {
            c.update(dt);
        }
    }

    public void setNonserializable() {
        serializable = false;
    }

    public void draw(Graphics2D g2) {
        for (Component c: components) {
            c.draw(g2);
        }
    }


    @Override
    public String serialize(int tabSize) {
        if(!serializable) return "";
        StringBuilder builder = new StringBuilder();

        // Game Object
        builder.append(beginObjectProperty("GameObject",tabSize));

        // Transform
        builder.append(transform.serialize(tabSize + 1));
        builder.append(addEnding(true, true));

        // Name
        if(components.size() > 0) {
            builder.append(addStringProperty("Name", name, tabSize + 1, true, true));
            builder.append(beginObjectProperty("Components", tabSize + 1));
        } else {
            builder.append(addStringProperty("Name", name, tabSize + 1, true, true));
        }

        int i = 0;
        for(Component c: components) {
            String str = c.serialize(tabSize + 2);
            if(str.compareTo("") != 0) {
                builder.append(str);
                if(i != components.size() -1) {
                    builder.append(addEnding(true,true));
                } else {
                    builder.append(addEnding(true, false));
                }
            }
            i++;
        }
        if(components.size() > 0) {
            builder.append(closeObjectProperty(tabSize + 1));
        }

        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static GameObject deserialize() {
        Parser.consumeBeginObjectProperty("GameObject");

        Transform transform = Transform.deserialize();
        Parser.consume(',');
        String name = Parser.consumeStringProperty("Name");

        GameObject go = new GameObject(name, transform);
        if(Parser.peek() == ',') {
            Parser.consume(',');
            Parser.consumeBeginObjectProperty("Components");
            go.addComponent(Parser.parseComponent());

            while (Parser.peek() == ',') {
                Parser.consume(',');
                go.addComponent(Parser.parseComponent());

            }

            Parser.consumeEndObjectProperty();
        }
        Parser.consumeEndObjectProperty();
        return go;
    }

    public void setUI(boolean val) {
        this.isUI = val;

    }
}
