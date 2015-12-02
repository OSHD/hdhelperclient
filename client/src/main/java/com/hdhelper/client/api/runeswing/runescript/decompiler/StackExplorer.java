package com.hdhelper.client.api.runeswing.runescript.decompiler;

import java.util.HashMap;
import java.util.PriorityQueue;

import static com.hdhelper.client.api.runeswing.runescript.RSOpcodes.*;

public class StackExplorer {


    private PriorityQueue<BranchTarget> paths;
    private HashMap<Integer, Integer> mindists;


    private final class BranchTarget implements Comparable {

        public int src;
        public int dist;
        public int loc;
        public BranchTarget parent;
        public boolean pass;

        public BranchTarget(int loc) {
            this.loc = loc;
            parent = null;
            dist = 0;
        }

        public BranchTarget(BranchTarget p, int src, int dist, int loc, boolean pass) {
            this(loc);
            parent = p;
            this.dist = dist;
            this.pass = pass;
            this.src = src;
        }

        public int compareTo(Object o) {
            BranchTarget p = (BranchTarget) o;
            int a = dist;
            int b = p.dist;
            return (a > b ? + 1 : a < b ? -1 : 0);
        }

        public int getLoc() {
            return loc;
        }

        public void setLoc(int p) {
            loc = p;
        }
    }


    int[] opcodes;
    int[] operands;

    int getBranchTarget(int i) {
        return i + operands[i];
    }

    private void expand(BranchTarget path) {

        int p = path.getLoc();

        Integer min = mindists.get(p);

        if (min == null || min > path.dist)
            mindists.put(path.getLoc(), path.dist);
        else return;

        int extra = 0;

        for (int pos = p; pos < opcodes.length; pos++) {
            int target,dist;
            switch (opcodes[pos]) {

                case IF_ICMPNE:
                case IF_ICMPEQ:
                case IF_ICMPLT:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ICMPGE:
                    target = getBranchTarget(pos);
                    dist = path.dist + (pos-p) + extra;
                    paths.offer(new BranchTarget(path, pos, dist, pos + 1, true));
                    paths.offer(new BranchTarget(path, pos, dist, target + 1, false));
                    return;
                case GOTO:
                    target = getBranchTarget(pos);
                    dist = path.dist + (pos-p) + extra;
                    paths.offer(new BranchTarget(path, pos, dist, target + 1, true));
                    return;
                case RETURN:
                    dist = path.dist + (pos-p) + extra;
                    paths.offer(new BranchTarget(path, pos, dist, -1, true));
                    return;
                case INVOKE:
                    extra += opcodes.length; // Increase the weight

            }

        }

        throw new InternalError("Expected Jump or Return before EOF");

    }

    public HashMap<Integer,Boolean> compute(int start, int[] opcodes, int[] operands) {

        if (start < 0 || opcodes == null || operands == null ||
                start > opcodes.length ||
                opcodes.length != operands.length)
            throw new Error("Nope");

        this.opcodes = opcodes;
        this.operands = operands;

        paths = new PriorityQueue<BranchTarget>();
        mindists = new HashMap<Integer,Integer>();

        try {

            BranchTarget root = new BranchTarget(start);
            expand(root);
          /*  if(expand(root) > 0) {
                System.out.println("HEEE");
            }
*/
            while (true) {

                BranchTarget p = paths.poll();



             //   System.out.println("NEXT:" + p);

                if (p == null) return null;

                if (p.loc == -1) {

                    HashMap<Integer,Boolean> ret = new HashMap<Integer,Boolean>();

                    for (BranchTarget i = p; i != null; i = i.parent) {
                        ret.put(i.src, i.pass);
                        //System.out.println(i.src + "=" + i.pass + " #" + i.dist);
                    }
                    //   System.out.println("Exit Found @ " + exit );

                    return ret;
                }


                expand(p);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
