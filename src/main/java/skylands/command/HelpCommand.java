package skylands.command;

import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Language;

public class HelpCommand {

	static void run(ServerPlayerEntity player) {
		Language lang = Language.getInstance();
		String text = "";
		String key = "message.skylands.help.";

		for(int i = 0; i <= 32; i++) {
			if(lang.hasTranslation(key + i)) {
				text = text + lang.get(key + i);
			}
			if(lang.hasTranslation(key + (i + 1))) {
				text = text + "\n";
			}
			else {
				break;
			}
		}
		player.sendMessage(TextParserUtils.formatText(text));
	}
}
