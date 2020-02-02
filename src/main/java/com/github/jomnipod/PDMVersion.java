package com.github.jomnipod;

import com.github.jomnipod.IBFRecord.Iterator;

public final class PDMVersion extends Version {
    public PDMVersion(Iterator iterator) {
       super(iterator.nextUnsignedBEShort(), iterator.nextUnsignedBEShort(), iterator.nextUnsignedBEShort());
    }
}
