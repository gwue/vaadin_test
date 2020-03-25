package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PileEntry extends CustomField<String> {

    enum PileState {
        NEW {

            @Override
            public PileState previous() {
                return null;
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
                return null;
            }
        };

        public abstract PileState previous();
        public abstract PileState next();
    }

    private final Button nextState, prevState;
    private final Label name;
    private PileState state;

    public PileEntry(String name) {
        this(name, PileState.NEW);
    }

    public PileEntry(String name, PileState state) {
        this.state = state;
        HorizontalLayout layout = new HorizontalLayout();
        nextState = new Button("N");
        nextState.addClickListener(e -> {
        });
        prevState = new Button("P");
        this.name = new Label(name);
        layout.add(this.name, nextState, prevState);
        add(layout);
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