package org.vaadin.example;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PileEntry extends CustomField<String> {

    /**
     *
     */
    private static final long serialVersionUID = -2825259205459205501L;

    enum PileState {
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
    private final Label name;
    private PileState state;
    private Set<PropertyChangeListener> listeners;

    public PileEntry(String name) {
        this(name, PileState.NEW);
    }

    public PileEntry(String name, PileState state) {
        this.listeners = new HashSet<>();
        this.state = state;
        HorizontalLayout layout = new HorizontalLayout();

        nextStateButton = new Button("N");
        nextStateButton.addClickListener(e -> {
            PileState old = this.state;
            this.state = this.state.next();
            processStateUpdate(old);
        });

        prevStateButton = new Button("P");
        prevStateButton.addClickListener(e -> {
            PileState old = this.state;
            this.state = this.state.previous();
            processStateUpdate(old);
        });

        processStateUpdate(this.state);
        this.name = new Label(name);

        layout.add(this.name, nextStateButton, prevStateButton);
        add(layout);
    }

    public void addStateListener(PropertyChangeListener l) {
        this.listeners.add(l);
    }

    public void removeStateListener(PropertyChangeListener l) {
        this.listeners.remove(l);
    }

    private void processStateUpdate(PileState oldState) {
        if (this.state == PileState.last()) {
            nextStateButton.setEnabled(false);
        } else {
            nextStateButton.setEnabled(true);
        }
        if (this.state == PileState.first()) {
            prevStateButton.setEnabled(false);
        } else {
            prevStateButton.setEnabled(true);
        }
        if (oldState != this.state) {
            for (PropertyChangeListener l : listeners) {
                PropertyChangeEvent evt = new PropertyChangeEvent(this, "state", oldState, this.state);
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