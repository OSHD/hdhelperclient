package com.hdhelper.agent;

/**
 * A special interface to mark service-types that are local to the client.
 * User code is to never implement this interface.
 *
 * Interfaces do not care about who defines the contract.
 * In some cases its asserted that the type in which defined the
 * interface is always the same (usually a client-native).
 * This interface allows for quick verification mechanic to see
 * if the implementing type is of its natural native type, and
 * not user-generated.
 */
public interface RTClass {
}
