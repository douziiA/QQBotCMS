package com.douzii.botcms.log;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import net.mamoe.mirai.utils.MiraiLogger;
import net.mamoe.mirai.utils.SimpleLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BotLogger extends SimpleLogger {
    public BotLogger(@Nullable String identity, @NotNull Function3<? super LogPriority, ? super String, ? super Throwable, Unit> logger) {
        super(identity, logger);
    }

}
