package main.gui.panels.main.tickets.config;

import java.io.File;
import java.util.List;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.panels.SwitchConfigPanel;
import main.gui.panels.main.tickets.TicketsSidePanels;

public class TicketsConfigPanel extends SwitchConfigPanel<TicketsSidePanels> {
	public TicketsConfigPanel(Runnable onChooseFunction) {
		Language lang = Window.getConfig().language;
		List<String> buttonTexts = List.of(
				lang.get("ticketsConfigButtonValid"),
				lang.get("ticketsConfigButtonInvalid"),
				lang.get("ticketsConfigButtonPurchase")
		);
		
		List<File> iconFiles = List.of(
				new File("assets/graphics/tickets/valid_button_icon.png"),
				new File("assets/graphics/tickets/invalid_button_icon.png"),
				new File("assets/graphics/tickets/purchase_button_icon.png")
		);
		
		List<TicketsSidePanels> buttonEnumValues = List.of(
				TicketsSidePanels.VALID,
				TicketsSidePanels.INVALID,
				TicketsSidePanels.PURCHASE
		);
		
		super(TicketsSidePanels.VALID, onChooseFunction, buttonTexts, iconFiles, buttonEnumValues);
	}
}
