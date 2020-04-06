package org.vaadin.example;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class PileEntry extends CustomField<String> {

    /**
     *
     */
    private static final long serialVersionUID = -2825259205459205501L;

    public enum PileState {
        NEW {

            @Override
            public PileState previous() {
                return NEW;
            }

            @Override
            public PileState next() {
                return WIP;
            }

        },
        WIP {

            @Override
            public PileState previous() {
                return NEW;
            }

            @Override
            public PileState next() {
                return DONE;
            }
        },
        DONE {
            @Override
            public PileState previous() {
                return WIP;
            }

            @Override
            public PileState next() {
                return DONE;
            }
        };

        public abstract PileState previous();

        public abstract PileState next();

        public static PileState first() {
            return NEW;
        }

        public static PileState last() {
            return DONE;
        }
    }

    private final Button nextStateButton, prevStateButton;
    private final Button deleteButton;
    private final Label name;
    private PileEntryData data;
    private Set<PropertyChangeListener> listeners;

    public PileEntry( PileEntryData data) {
        this.data=data;
        this.listeners = new HashSet<>();
        HorizontalLayout layout = new HorizontalLayout();

        deleteButton = new Button("D");
        nextStateButton = new Button("N");
        nextStateButton.addClickListener(e -> {
            PileState old = this.data.getState();
            this.data.setState(this.data.getState().next());
            processStateUpdate(old);
        });

        prevStateButton = new Button("P");
        prevStateButton.addClickListener(e -> {
            PileState old = this.data.getState();
            this.data.setState(this.data.getState().previous());
            processStateUpdate(old);
        });

        processStateUpdate(this.data.getState());
        this.name = new Label(data.getName());

        layout.add(this.name, nextStateButton, prevStateButton, deleteButton);
        add(layout);
    }

    public void addStateListener(PropertyChangeListener l) {
        this.listeners.add(l);
    }

    public void removeStateListener(PropertyChangeListener l) {
        this.listeners.remove(l);
    }

    public void addDeleteListener(ComponentEventListener<ClickEvent<Button>> l) {
        this.deleteButton.addClickListener(l);
    }

    private void processStateUpdate(PileState oldState) {
        if (this.data.getState() == PileState.last()) {
            nextStateButton.setEnabled(false);
        } else {
            nextStateButton.setEnabled(true);
        }
        if (this.data.getState() == PileState.first()) {
            prevStateButton.setEnabled(false);
        } else {
            prevStateButton.setEnabled(true);
        }
        if (oldState != this.data.getState()) {
            for (PropertyChangeListener l : listeners) {
                PropertyChangeEvent evt = new PropertyChangeEvent(this, "state", oldState, this.data.getState());
                l.propertyChange(evt);
            }
        }
    }

    @Override
    protected String generateModelValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void setPresentationValue(String newPresentationValue) {
        // TODO Auto-generated method stub

    }

}