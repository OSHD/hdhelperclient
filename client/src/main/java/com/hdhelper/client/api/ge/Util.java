package com.hdhelper.client.api.ge;

public class Util {

    public static int method305(CharSequence var0, int var1) {

        return method115(var0, 10, true, (byte) 115);

    }

    static int method115(CharSequence var0, int var1, boolean var2, byte var3) {

        if (var1 >= 2 && var1 <= 36) {
            boolean var4 = false;
            boolean var5 = false;
            int var6 = 0;
            int var7 = var0.length();
            int var8 = 0;

            while (true) {
                if (var8 >= var7) {
                    if (!var5) {
                        if (var3 <= 1) {
                            throw new IllegalStateException();
                        }

                        throw new NumberFormatException();
                    }

                    return var6;
                }

                if (var3 <= 1) {
                    throw new IllegalStateException();
                }

                label138:
                {
                    char var9 = var0.charAt(var8);
                    if (0 == var8) {
                        if (45 == var9) {
                            if (var3 <= 1) {
                                throw new IllegalStateException();
                            }

                            var4 = true;
                            break label138;
                        }

                        if (43 == var9) {
                            if (var3 <= 1) {
                                throw new IllegalStateException();
                            }

                            if (var2) {
                                if (var3 <= 1) {
                                    throw new IllegalStateException();
                                }
                                break label138;
                            }
                        }
                    }

                    int var12;
                    label139:
                    {
                        if (var9 >= 48) {
                            if (var3 <= 1) {
                                throw new IllegalStateException();
                            }

                            if (var9 <= 57) {
                                if (var3 <= 1) {
                                    throw new IllegalStateException();
                                }

                                var12 = var9 - 48;
                                break label139;
                            }
                        }

                        if (var9 >= 65 && var9 <= 90) {
                            if (var3 <= 1) {
                                throw new IllegalStateException();
                            }

                            var12 = var9 - 55;
                        } else {
                            if (var9 < 97) {
                                break;
                            }

                            if (var3 <= 1) {
                                throw new IllegalStateException();
                            }

                            if (var9 > 122) {
                                break;
                            }

                            if (var3 <= 1) {
                                throw new IllegalStateException();
                            }

                            var12 = var9 - 87;
                        }
                    }

                    if (var12 >= var1) {
                        if (var3 <= 1) {
                            throw new IllegalStateException();
                        }

                        throw new NumberFormatException();
                    }

                    if (var4) {
                        var12 = -var12;
                    }

                    int var10 = var12 + var6 * var1;
                    if (var10 / var1 != var6) {
                        if (var3 <= 1) {
                            throw new IllegalStateException();
                        }

                        throw new NumberFormatException();
                    }

                    var6 = var10;
                    var5 = true;
                }

                ++var8;
            }

            throw new NumberFormatException();
        } else {
            throw new IllegalArgumentException("");
        }
    }


    public static byte method264(char var0, byte var1) {

            byte var2;
            label207:
            {
                if (var0 > 0) {
                    if (var1 == 27) {
                        throw new IllegalStateException();
                    }

                    if (var0 < 128) {
                        break label207;
                    }

                    if (var1 == 27) {
                        throw new IllegalStateException();
                    }
                }

                if (var0 < 160 || var0 > 255) {
                    if (var0 == 8364) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -128;
                        return var2;
                    } else if (8218 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -126;
                        return var2;
                    } else if (402 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -125;
                        return var2;
                    } else if (var0 == 8222) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -124;
                        return var2;
                    } else if (var0 == 8230) {
                        var2 = -123;
                        return var2;
                    } else if (8224 == var0) {
                        var2 = -122;
                        return var2;
                    } else if (8225 == var0) {
                        var2 = -121;
                        return var2;
                    } else if (var0 == 710) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -120;
                        return var2;
                    } else if (var0 == 8240) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -119;
                        return var2;
                    } else if (var0 == 352) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -118;
                        return var2;
                    } else if (8249 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -117;
                        return var2;
                    } else if (338 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -116;
                        return var2;
                    } else if (381 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -114;
                        return var2;
                    } else if (var0 == 8216) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -111;
                        return var2;
                    } else if (8217 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -110;
                        return var2;
                    } else if (var0 == 8220) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -109;
                        return var2;
                    } else if (8221 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -108;
                        return var2;
                    } else if (8226 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -107;
                        return var2;
                    } else if (8211 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -106;
                        return var2;
                    } else if (var0 == 8212) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -105;
                        return var2;
                    } else if (var0 == 732) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -104;
                        return var2;
                    } else if (var0 == 8482) {
                        var2 = -103;
                        return var2;
                    } else if (var0 == 353) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -102;
                        return var2;
                    } else if (8250 == var0) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -101;
                        return var2;
                    } else if (var0 == 339) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -100;
                        return var2;
                    } else if (var0 == 382) {
                        if (var1 == 27) {
                            throw new IllegalStateException();
                        }

                        var2 = -98;
                        return var2;
                    } else {
                        if (376 == var0) {
                            var2 = -97;
                        } else {
                            var2 = 63;
                        }

                        return var2;
                    }
                }

                if (var1 == 27) {
                    throw new IllegalStateException();
                }
            }

            var2 = (byte) var0;
            return var2;
        }


    public static int method69(CharSequence var0, int var1) {
        return method115(var0, var1, true, (byte) 44);
    }

}
