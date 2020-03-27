package org.vaadin.example;

import javax.sql.DataSource;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.PileEntry.PileState;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean. Use the @PWA
 * annotation make the application installable on phones, tablets and some
 * desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 */
@Route
@PWA(name = "Vaadin Application", shortName = "Vaadin App", description = "This is an example Vaadin application.", enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends HorizontalLayout {

    /**
     *
     */
    private static final long serialVersionUID = 3181099347188630562L;

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed
     *                bean.
     */
    public MainView(@Autowired GreetService service, @Autowired TestRepository tr) {

        Header h1 = new Header(), h2 = new Header(), h3 = new Header();
        h1.add("Pile of shame");
        h2.add("In Progress");
        h3.add("Done!");

        VerticalLayout v1 = new VerticalLayout(), v2 = new VerticalLayout(), v3 = new VerticalLayout();

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");


        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            PileEntry entry = new PileEntry(textField.getValue());
            v1.addComponentAtIndex(1, entry);
            entry.addStateListener(l -> {
                PileState oldState = (PileState) l.getOldValue();
                PileState newState = (PileState) l.getNewValue();
                switch (oldState) {
                    case NEW:
                        v1.remove(entry);
                        break;
                    case WIP:
                        v2.remove(entry);
                        break;
                    case DONE:
                        v3.remove(entry);
                }
                switch(newState) {
                    case NEW:
                        v1.addComponentAtIndex(1,entry);
                        break;
                    case WIP:
                        v2.addComponentAtIndex(1,entry);
                        break;
                    case DONE:
                        v3.addComponentAtIndex(1,entry);
                }

            });
        });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in
        // shared-styles.css.
        addClassName("centered-content");

        v1.add(h1, textField, button);
        v2.add(h2);
        v3.add(h3);

        add(v1, v2, v3);
        System.out.println(tr);
        TestData td = new TestData();
        tr.save(td);
        
        System.out.println(tr.count()); 
    }

}
