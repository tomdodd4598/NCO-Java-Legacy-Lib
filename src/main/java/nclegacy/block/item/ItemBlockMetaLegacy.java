package nclegacy.block.item;

import java.util.List;

import javax.annotation.Nullable;

import nc.block.IBlockMeta;
import nc.util.*;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class ItemBlockMetaLegacy extends ItemBlock {
	
	private final TextFormatting[] fixedColors;
	private final TextFormatting infoColor;
	public final String[][] fixedInfo, info;
	
	public <T extends Enum<T> & IStringSerializable> ItemBlockMetaLegacy(Block block, Class<T> enumm, TextFormatting[] fixedColors, String[][] fixedTooltips, TextFormatting infoColor, String[]... tooltips) {
		super(block);
		if (!(block instanceof IBlockMeta)) {
			throw new IllegalArgumentException(String.format("The block %s for this ItemBlockMeta is not an instance of IBlockMeta!", block.getTranslationKey()));
		}
		setMaxDamage(0);
		setHasSubtypes(true);
		this.fixedColors = fixedColors;
		fixedInfo = InfoHelper.buildFixedInfo(block.getTranslationKey(), enumm, fixedTooltips);
		this.infoColor = infoColor;
		info = InfoHelper.buildInfo(block.getTranslationKey(), enumm, tooltips);
	}
	
	public <T extends Enum<T> & IStringSerializable> ItemBlockMetaLegacy(Block block, Class<T> enumm, TextFormatting fixedColor, String[][] fixedTooltips, TextFormatting infoColor, String[]... tooltips) {
		this(block, enumm, new TextFormatting[] {fixedColor}, fixedTooltips, infoColor, tooltips);
	}
	
	public <T extends Enum<T> & IStringSerializable> ItemBlockMetaLegacy(Block block, Class<T> enumm, TextFormatting infoColor, String[]... tooltips) {
		this(block, enumm, TextFormatting.RED, InfoHelper.EMPTY_ARRAYS, infoColor, tooltips);
	}
	
	public <T extends Enum<T> & IStringSerializable> ItemBlockMetaLegacy(Block block, Class<T> enumm, String[]... tooltips) {
		this(block, enumm, TextFormatting.RED, InfoHelper.EMPTY_ARRAYS, TextFormatting.AQUA, tooltips);
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		return getTranslationKey() + "." + ((IBlockMeta) block).getMetaName(stack);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(stack, world, tooltip, flag);
		int meta = StackHelper.getMetadata(stack);
		if (fixedInfo.length > meta && info.length > meta) {
			if (fixedColors.length == 1) {
				InfoHelper.infoFull(tooltip, fixedColors[0], fixedInfo[meta], infoColor, info[meta]);
			}
			else {
				InfoHelper.infoFull(tooltip, fixedColors, fixedInfo[meta], infoColor, info[meta]);
			}
		}
	}
}
