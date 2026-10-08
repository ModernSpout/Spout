package spout.gamecontent.datadriven.common.enuminjection.pluginbytecode;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

public class EnumSwitchGuard extends ClassNode {

    private static final String HELPER = "spout/gamecontent/datadriven/common/enuminjection/pluginbytecode/EnumSwitchHelper";
    private final ClassVisitor next;

    public EnumSwitchGuard(ClassVisitor next) {
        super(Opcodes.ASM9);
        this.next = next;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        for (MethodNode mn : methods) {
            if (mn.instructions.size() == 0) continue; // abstract/native

            List<AbstractInsnNode> targets = new ArrayList<>();
            for (AbstractInsnNode insn : mn.instructions) {
                if (insn.getOpcode() != Opcodes.IALOAD) continue;

                AbstractInsnNode prev = prevReal(insn);
                AbstractInsnNode nxt = nextReal(insn);

                if (prev instanceof MethodInsnNode m
                        && m.getOpcode() == Opcodes.INVOKEVIRTUAL
                        && m.name.equals("ordinal")
                        && m.desc.equals("()I")
                        && (nxt instanceof TableSwitchInsnNode || nxt instanceof LookupSwitchInsnNode)) {
                    targets.add(insn);
                }
            }

            for (AbstractInsnNode iaload : targets) {
                mn.instructions.set(iaload, new MethodInsnNode(
                        Opcodes.INVOKESTATIC, HELPER, "safeLoad", "([II)I", false));
            }
        }
        accept(next);
    }

    private static AbstractInsnNode prevReal(AbstractInsnNode n) {
        n = n.getPrevious();
        while (n != null && n.getOpcode() == -1) n = n.getPrevious();
        return n;
    }

    private static AbstractInsnNode nextReal(AbstractInsnNode n) {
        n = n.getNext();
        while (n != null && n.getOpcode() == -1) n = n.getNext();
        return n;
    }

}
