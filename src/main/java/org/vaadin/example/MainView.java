package org.vaadin.example;

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
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
//@NoTheme
@Theme(Material.class)
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
    public MainView(@Autowired ApplicationContext context, @Autowired GreetService service, @Autowired PileEntryDataRepository repo) {

        Header h1 = new Header(), h2 = new Header(), h3 = new Header();
        h1.add("Pile of shame");
        h2.add("In Progress");
        h3.add("Done!");

        VerticalLayout v1 = new VerticalLayout(), v2 = new VerticalLayout(), v3 = new VerticalLayout();

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            PileEntryData pileEntryData = new PileEntryData(textField.getValue());
            PileEntry entry = new PileEntry(pileEntryData);
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
            entry.addDeleteListener(l -> {
                v1.getChildren().filter(c -> c.equals(entry)).forEach(c -> v1.remove(c));
                v2.getChildren().filter(c -> c.equals(entry)).forEach(c -> v2.remove(c));
                v3.getChildren().filter(c -> c.equals(entry)).forEach(c -> v3.remove(c));
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
    }

}
