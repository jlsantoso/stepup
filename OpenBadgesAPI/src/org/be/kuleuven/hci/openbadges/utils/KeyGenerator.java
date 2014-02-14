package org.be.kuleuven.hci.openbadges.utils;

import java.security.SecureRandom;
import java.math.BigInteger;

public final class KeyGenerator
{

  private SecureRandom random = new SecureRandom();

  public String nextKeyId()
  {
    return new BigInteger(130, random).toString(32);
  }

}
