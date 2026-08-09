package com.blacklizardcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.Identifier;

public class XaeroNuhUhCommon {
    public static final String MOD_ID = "xaeronuhuh";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String VERSION = /*$ mod_version*/ "0.1.0";
    public static final String MINECRAFT = /*$ minecraft*/ "26.2";
    public static Identifier id(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}