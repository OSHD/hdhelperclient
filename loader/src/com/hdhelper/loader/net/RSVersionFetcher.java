package com.hdhelper.loader.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

// Looks up the current revision of the oldschool game-client.
public class RSVersionFetcher {

    private static final int PORT = 43594;

    public static int getVersion(int initial_major, int world, int timeout, int max_attempts) throws IOException {

        final String address = getServerAddress( world );

        Socket socket;
        InputStream input;
        OutputStream output;

        int version = initial_major;
        int attempt = 0;

        while(attempt < max_attempts) {

            socket = new Socket();
            socket.connect(new InetSocketAddress(address, PORT), timeout);
            input  = socket.getInputStream();
            output = socket.getOutputStream();

            ByteBuffer handshake = mkHandshake(version);
            output.write(handshake.array());
            output.flush();

            while(true) {
                //Wait for a response:
                if (input.available() > 0) {
                    final int response = input.read();
                    if (response == 0) { // Correct version
                        return version;
                    } else if (response == 6) { // Outdated
                        version++;
                        socket.close();
                        break;
                    } else { // Unknown response
                        throw new IOException( "Unexpected reponse:" + response );
                    }
                }
            }

            attempt++;

        }

        return -1;

    }

    private static ByteBuffer mkHandshake(int major_version) {
        ByteBuffer buffer = ByteBuffer.allocate( 4 + 1 );
        buffer.put((byte) 15);         // handshake type ------- 1
        buffer.putInt(major_version);  // major version -------- 4
        return buffer;
    }


    private static String getServerAddress( int world  ) {
        return "oldschool" + world + ".runescape.com";
    }

}