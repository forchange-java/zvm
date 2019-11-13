package com.zvm;

import com.google.gson.Gson;
import com.zvm.basestruct.u1;
import com.zvm.basestruct.u4;
import com.zvm.draft.Opcode1;
import com.zvm.runtime.*;
import com.zvm.runtime.struct.JObject;

import java.util.ArrayList;
import java.util.List;

public class Interpreter {

    public RunTimeEnv runTimeEnv;
    public JThread jThread ;


    public Interpreter(RunTimeEnv runTimeEnv){
        this.runTimeEnv = runTimeEnv;
        jThread = new JThread();
    }

    public void invokeByName(JavaClass javaClass, String name, String descriptor){
        method_info method_info = javaClass.findMethod(name, descriptor);
        if (method_info == null){
            return ;
        }
        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);

        jThread.pushFrame(callSite.max_stack, callSite.max_locals);
        executeByteCode(jThread, javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.code_length.u4));
    }

    public void executeByteCode(JThread jThread, JavaClass javaClass, u1[] codeRaw, int codeLength) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;
        CodeUtils code = new CodeUtils(codeRaw, 0);
        for (; code.getPc() < codeLength; code.pcAdd(1)) {
            int opcodeInt = TypeUtils.byteArr2Int(codeRaw[code.getPc()].u1);
            Gson gson = new Gson();
            System.out.println("pc = " + code.getPc() + " operandStack "+gson.toJson(operandStack));
            System.out.println("pc = " + code.getPc() + " localVars " + gson.toJson(localVars));
            System.out.println();
            System.out.println("pc = " + code.getPc() + " opcode:" + Opcode1.getMnemonic(opcodeInt));


            switch (opcodeInt) {
                case Opcode.nop: {

                }
                break;
                case Opcode.aconst_null: {

                }
                break;
                case Opcode.iconst_m1: {
                }
                break;
                case Opcode.iconst_0: {
                    operandStack.putInt(0);
                }
                break;
                case Opcode.iconst_1: {
                    operandStack.putInt(1);
                }
                break;
                case Opcode.iconst_2: {
                    operandStack.putInt(2);

                }
                break;
                case Opcode.iconst_3: {
                    operandStack.putInt(3);

                }
                break;
                case Opcode.iconst_4: {
                    operandStack.putInt(4);
                }
                break;
                case Opcode.iconst_5: {
                    operandStack.putInt(5);
                }
                break;
                case Opcode.lconst_0: {
                    operandStack.putLong(0);
                }
                break;
                case Opcode.bipush: {
                    byte byteConstant = code.consumeU1();
                    operandStack.putByte(byteConstant);
                }
                break;
                case Opcode.sipush: {
                }
                break;
                case Opcode.ldc: {
                    /*从常量池取值到frame顶*/
                    byte cpIndex = code.consumeU1();
                    CONSTANT_Base constant_base = javaClass.getClassFile().constant_pool.cp_info[TypeUtils.byte2Int(cpIndex)-1];
                    if(constant_base instanceof CONSTANT_Integer){
                        u4 ldcBytes = ((CONSTANT_Integer) constant_base).bytes;
                        Integer ldcValue = TypeUtils.byteArr2Int(ldcBytes.u4);
                        operandStack.putInt(ldcValue);
                    }else if(constant_base instanceof CONSTANT_Float){
                        u4 ldcBytes = ((CONSTANT_Float) constant_base).bytes;
                        float ldcValue = TypeUtils.byteArr2Float(ldcBytes.u4);
                        operandStack.putFloat(ldcValue);
                    }

                }
                break;
                case Opcode.ldc_w: {
                    /*从常量池取值到frame顶*/
                    short cpIndex = code.consumeU2();
                    CONSTANT_Base constant_base = javaClass.getClassFile().constant_pool.cp_info[cpIndex - 1];
                    if(constant_base instanceof CONSTANT_Integer){
                        u4 ldcBytes = ((CONSTANT_Integer) constant_base).bytes;
                        Integer ldcValue = TypeUtils.byteArr2Int(ldcBytes.u4);
                        operandStack.putInt(ldcValue);
                    }else if(constant_base instanceof CONSTANT_Float){
                        u4 ldcBytes = ((CONSTANT_Float) constant_base).bytes;
                        float ldcValue = TypeUtils.byteArr2Float(ldcBytes.u4);
                        operandStack.putFloat(ldcValue);
                    }
                }
                break;
                case Opcode.ldc2_w: {
                    /*从常量池取值到frame顶*/
                    short cpIndex = code.consumeU2();
                    CONSTANT_Base constant_base = javaClass.getClassFile().constant_pool.cp_info[cpIndex - 1];
                    if(constant_base instanceof CONSTANT_Long){
                        u4 highBytes = ((CONSTANT_Long) constant_base).high_bytes;
                        u4 lowBytes = ((CONSTANT_Long) constant_base).low_bytes;
                        operandStack.putLong(TypeUtils.byteArr2Int(highBytes.u4), TypeUtils.byteArr2Int(lowBytes.u4));
                    }else if(constant_base instanceof CONSTANT_Double){
                        u4 highBytes = ((CONSTANT_Double) constant_base).high_bytes;
                        u4 lowBytes = ((CONSTANT_Double) constant_base).low_bytes;
                        byte[] doubleByte = TypeUtils.appendByte(highBytes.u4, lowBytes.u4);
                        double ldcValue = TypeUtils.byteArr2Double(doubleByte);
                        operandStack.putDouble(ldcValue);
                    }
                }
                break;
                case Opcode.iload: {
                }
                break;
                case Opcode.lload: {
                }
                break;
                case Opcode.fload: {
                }
                break;
                case Opcode.dload: {
                }
                break;
                case Opcode.aload: {
                }
                break;
                case Opcode.lload_2: {
                }
                break;
                case Opcode.lload_3: {
                }
                break;
                case Opcode.fload_0: {
                }
                break;
                case Opcode.fload_1: {
                }
                break;
                case Opcode.fload_2: {
                }
                break;
                case Opcode.fload_3: {
                }
                break;
                case Opcode.dload_0: {
                }
                break;
                case Opcode.dload_1: {
                }
                break;
                case Opcode.dload_2: {
                }
                break;
                case Opcode.dload_3: {
                }
                break;
                case Opcode.faload: {
                }
                break;
                case Opcode.daload: {
                }
                break;
                case Opcode.aaload: {
                }
                break;
                case Opcode.baload: {
                }
                break;
                case Opcode.caload: {
                }
                break;
                case Opcode.saload: {
                }
                break;
                case Opcode.istore: {
                }
                break;
                case Opcode.lstore: {
                }
                break;
                case Opcode.fstore: {
                }
                break;
                case Opcode.dstore: {
                }
                break;
                case Opcode.lstore_1: {
                    localVars.putLong(1, operandStack.popLong());
                }
                break;
                case Opcode.lstore_2: {
                    localVars.putLong(2, operandStack.popLong());
                }
                break;
                case Opcode.lstore_3: {
                    localVars.putLong(3, operandStack.popLong());
                }
                break;
                case Opcode.fstore_0: {
                }
                break;
                case Opcode.fstore_1: {
                }
                break;
                case Opcode.fstore_2: {
                }
                break;
                case Opcode.fstore_3: {
                }
                break;
                case Opcode.dstore_0: {
                }
                break;
                case Opcode.dstore_1: {
                }
                break;
                case Opcode.dstore_2: {
                }
                break;
                case Opcode.lastore: {
                }
                break;
                case Opcode.fastore: {
                }
                break;
                case Opcode.dastore: {
                }
                break;
                case Opcode.aastore: {
                }
                break;
                case Opcode.bastore: {
                }
                break;
                case Opcode.castore: {
                }
                break;
                case Opcode.sastore: {
                }
                break;
                case Opcode.pop: {
                }
                break;
                case Opcode.pop2: {
                }
                break;
                case Opcode.dup: {
                    operandStack.putSlot(operandStack.getSlot());
                }
                break;
                case Opcode.iadd: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    int sum = var0 + var1;
                    operandStack.putInt(sum);
                }
                break;
                case Opcode.ladd: {
                    long var1 = operandStack.popLong();
                    long var0 = operandStack.popLong();
                    long addValue = var0 + var1;
                    operandStack.putLong(addValue);
                }
                break;
                case Opcode.fadd: {
                }
                break;
                case Opcode.dadd: {
                }
                break;
                case Opcode.isub: {
                }
                break;
                case Opcode.lsub: {
                    long var1 = operandStack.popLong();
                    long var0 = operandStack.popLong();
                    long subValue = var0 - var1;
                    operandStack.putLong(subValue);
                }
                break;
                case Opcode.fsub: {
                }
                break;
                case Opcode.dsub: {
                }
                break;
                case Opcode.imul: {
                }
                break;
                case Opcode.lmul: {
                }
                break;
                case Opcode.irem: {
                }
                break;
                case Opcode.lrem: {
                }
                break;
                case Opcode.frem: {
                }
                break;
                case Opcode.drem: {
                }
                break;
                case Opcode.ineg: {
                }
                break;
                case Opcode.lneg: {
                }
                break;
                case Opcode.fneg: {
                }
                break;
                case Opcode.dneg: {
                }
                break;
                case Opcode.ishl: {
                }
                break;
                case Opcode.lshl: {
                }
                break;
                case Opcode.ior: {
                }
                break;
                case Opcode.lor: {
                }
                break;
                case Opcode.ixor: {
                }
                break;
                case Opcode.lxor: {
                }
                break;
                case Opcode.iinc: {
                    byte localVarIndex = code.consumeU1();
                    byte constValue = code.consumeU1();
                    int localVar = localVars.getIntByIndex(localVarIndex);
                    localVars.putIntByIndex(localVarIndex, localVar + constValue);
                }
                break;
                case Opcode.i2l: {
                }
                break;
                case Opcode.i2f: {
                }
                break;
                case Opcode.i2d: {
                }
                break;
                case Opcode.l2i: {
                }
                break;
                case Opcode.l2f: {
                }
                break;
                case Opcode.d2f: {
                }
                break;
                case Opcode.i2b: {
                }
                break;
                case Opcode.i2c: {
                }
                break;
                case Opcode.i2s: {
                }
                break;
                case Opcode.lcmp: {
                    long var1 = operandStack.popLong();
                    long var0 = operandStack.popLong();
                    int cmpRes = 0;
                    if(var0 > var1){
                        cmpRes = 1;
                    }else if(var0 < var1){
                        cmpRes = -1;
                    }
                    operandStack.putInt(cmpRes);
                }
                break;
                case Opcode.fcmpl: {
                }
                break;
                case Opcode.fcmpg: {
                }
                break;
                case Opcode.dcmpl: {
                }
                break;
                case Opcode.dcmpg: {
                }
                break;
                case Opcode.ifeq: {
                    int ifeqValue = operandStack.popInt();
                    if(ifeqValue == 0){
                        int offset = code.readU2();
                        code.pcAddSubOne(offset);
                    }else {
                        code.pcAdd(2);
                    }

                }
                break;
                case Opcode.lconst_1: {
                    operandStack.putLong(1);
                }
                break;
                case Opcode.fconst_0: {
                }
                break;
                case Opcode.fconst_1: {
                }
                break;
                case Opcode.fconst_2: {
                }
                break;
                case Opcode.dconst_0: {
                }
                break;
                case Opcode.dconst_1: {
                }
                break;
                case Opcode.iload_0: {
                    operandStack.putInt(localVars.getIntByIndex(0));
                }
                break;
                case Opcode.iload_1: {
                    operandStack.putInt(localVars.getIntByIndex(1));
                }
                break;
                case Opcode.iload_2: {
                    operandStack.putInt(localVars.getIntByIndex(2));
                }
                break;
                case Opcode.iload_3: {
                    operandStack.putInt(localVars.getIntByIndex(3));
                }
                break;
                case Opcode.lload_0: {
                    long loadValue = localVars.getLongByIndex(0);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.lload_1: {
                    long loadValue = localVars.getLongByIndex(1);
                    operandStack.putLong(loadValue);
                }
                break;
                case Opcode.aload_0: {
                    operandStack.putInt(localVars.getIntByIndex(3));
                }
                break;
                case Opcode.aload_1: {
                }
                break;
                case Opcode.aload_2: {
                }
                break;
                case Opcode.aload_3: {
                }
                break;
                case Opcode.iaload: {
                }
                break;
                case Opcode.laload: {
                }
                break;
                case Opcode.astore: {
                }
                break;
                case Opcode.istore_0: {
                    localVars.putIntByIndex(0, operandStack.popInt());
                }
                break;
                case Opcode.istore_1: {
                    localVars.putIntByIndex(1, operandStack.popInt());
                }
                break;
                case Opcode.istore_2: {
                    localVars.putIntByIndex(2, operandStack.popInt());
                }
                break;
                case Opcode.istore_3: {
                    localVars.putIntByIndex(3, operandStack.popInt());

                }
                break;
                case Opcode.lstore_0: {
                    localVars.putLong(0, operandStack.popLong());
                }
                break;
                case Opcode.dstore_3: {
                }
                break;
                case Opcode.astore_0: {
                }
                break;
                case Opcode.astore_1: {
                }
                break;
                case Opcode.astore_2: {
                }
                break;
                case Opcode.astore_3: {
                }
                break;
                case Opcode.iastore: {
                }
                break;
                case Opcode.dup_x1: {
                }
                break;
                case Opcode.dup_x2: {
                }
                break;
                case Opcode.dup2: {
                }
                break;
                case Opcode.dup2_x1: {
                }
                break;
                case Opcode.dup2_x2: {
                }
                break;
                case Opcode.swap: {
                }
                break;
                case Opcode.fmul: {
                }
                break;
                case Opcode.dmul: {
                }
                break;
                case Opcode.idiv: {
                }
                break;
                case Opcode.ldiv: {
                }
                break;
                case Opcode.fdiv: {
                }
                break;
                case Opcode.ddiv: {
                }
                break;
                case Opcode.ishr: {
                }
                break;
                case Opcode.lshr: {
                }
                break;
                case Opcode.iushr: {
                }
                break;
                case Opcode.lushr: {
                }
                break;
                case Opcode.iand: {
                }
                break;
                case Opcode.land: {
                }
                break;
                case Opcode.l2d: {
                }
                break;
                case Opcode.f2i: {
                }
                break;
                case Opcode.f2l: {
                }
                break;
                case Opcode.f2d: {
                }
                break;
                case Opcode.d2i: {
                }
                break;
                case Opcode.d2l: {
                }
                break;
                case Opcode.ifne: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 != 0){
                        code.pcAddSubOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.iflt: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 < 0){
                        code.pcAddSubOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.ifge: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 >= 0){
                        code.pcAddSubOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.ifgt: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 > 0){
                        code.pcAddSubOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.ifle: {
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 <= 0){
                        code.pcAddSubOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.if_icmpeq: {
                }
                break;
                case Opcode.if_icmpne: {
                }
                break;
                case Opcode.if_icmplt: {
                }
                break;
                case Opcode.if_icmpge: {
                }
                break;
                case Opcode.if_icmpgt: {
                    int var1 = operandStack.popInt();
                    int var0 = operandStack.popInt();
                    short offset = code.readU2();
                    if(var0 >= var1){
                        /*分支*/
                        code.pcAddSubOne(offset);
                    }else {
                        code.pcAdd(2);
                    }
                }
                break;
                case Opcode.if_icmple: {
                }
                break;
                case Opcode.if_acmpeq: {
                }
                break;
                case Opcode.if_acmpne: {
                }
                break;
                case Opcode.goto_: {
                    short offset = code.readU2();
                    code.pcAddSubOne(offset );
                }
                break;
                case Opcode.jsr: {
                }
                break;
                case Opcode.ret: {
                }
                break;
                case Opcode.tableswitch: {
                }
                break;
                case Opcode.lookupswitch: {
                }
                break;
                case Opcode.ireturn: {
                }
                break;
                case Opcode.lreturn: {
                    JavaFrame curFrame = jThread.popFrame();
                    JavaFrame invokerFrame = jThread.getTopFrame();
                    long val = operandStack.popLong();
                    invokerFrame.operandStack.putLong(val);
                    return;
                }
                case Opcode.freturn: {
                }
                break;
                case Opcode.dreturn: {
                }
                break;
                case Opcode.areturn: {
                }
                break;
                case Opcode.return_: {
                }
                break;
                case Opcode.getstatic: {
                    short staticIndex = code.consumeU2();
                    ClassFile classFile = javaClass.getClassFile();
                    CONSTANT_Base[] constant_bases = classFile.constant_pool.cp_info;
                    CONSTANT_Base constant_base = constant_bases[staticIndex - 1];
                    CONSTANT_Fieldref fieldref  = (CONSTANT_Fieldref) constant_base;
                    int class_index = TypeUtils.byteArr2Int(fieldref.class_index.u2);
                    int name_and_type_index = TypeUtils.byteArr2Int(fieldref.name_and_type_index.u2);

                    CONSTANT_Class constant_class = (CONSTANT_Class) constant_bases[class_index - 1];
                    CONSTANT_NameAndType constant_nameAndType = (CONSTANT_NameAndType)constant_bases[name_and_type_index - 1];

                    CONSTANT_Utf8 fieldNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.name_index.u2) - 1];
                    CONSTANT_Utf8 descriptorNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.descriptor_index.u2) - 1];
                    CONSTANT_Utf8 classNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_class.name_index.u2) - 1];

                    String fieldName = TypeUtils.u12String(fieldNameUtf8.bytes);
                    String descriptorName = TypeUtils.u12String(descriptorNameUtf8.bytes);
                    String className = TypeUtils.u12String(classNameUtf8.bytes);
                    field_info field_info = parseFieldRef(className, descriptorName, fieldName);
                    int slotId = field_info.slotId;
                    JavaClass javaClass1 = runTimeEnv.methodArea.findClass(className);
                    StaticVars staticVars = javaClass1.staticVars;
                    char s = descriptorName.charAt(0);
                    if(s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I'){
                        operandStack.putInt(staticVars.getIntByIndex(slotId));
                    }else if ( s == 'J' ){
                        operandStack.putLong(staticVars.getLongByIndex(slotId));
                    }else if (s == 'F'){
                        operandStack.putFloat(staticVars.getFloat(slotId));
                    }else if (s == 'D'){
                        operandStack.putDouble(staticVars.getDouble(slotId));
                    }else if(s == 'L' || s== '['){
                        /*bug*/
                        operandStack.putJObject(staticVars.getJObject(slotId));
                    }

                }
                break;
                case Opcode.putstatic: {
                }
                break;
                case Opcode.getfield: {
                }
                break;
                case Opcode.putfield: {
                }
                break;
                case Opcode.invokevirtual: {
                    short invokeIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_base = constant_bases[invokeIndex - 1];
                    invokeVirtual(javaClass,constant_base);
                }
                break;
                case Opcode.invokespecial: {
                    short invokeIndex = code.consumeU2();

                }
                break;
                case Opcode.invokestatic: {
                    short invokeIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_base = constant_bases[invokeIndex - 1];
                    invokeStatic(javaClass,constant_base);

                }
                break;
                case Opcode.invokeinterface: {
                }
                break;
                case Opcode.xxxunusedxxx: {
                }
                break;
                case Opcode.new_: {
                    int newIndex = code.consumeU2();
                    CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
                    CONSTANT_Base constant_base = constant_bases[newIndex - 1];
                    int name_index = TypeUtils.byteArr2Int(((CONSTANT_Class) constant_base).name_index.u2);
                    CONSTANT_Utf8 constant_utf8 = (CONSTANT_Utf8) constant_bases[name_index - 1];
                    JObject jObject = execNew(javaClass,constant_utf8);
                    operandStack.putJObject(jObject);
                }
                break;
                case Opcode.newarray: {
                }
                break;
                case Opcode.anewarray: {
                }
                break;
                case Opcode.arraylength: {
                }
                break;
                case Opcode.athrow: {
                }
                break;
                case Opcode.checkcast: {
                }
                break;
                case Opcode.instanceof_: {
                }
                break;
                case Opcode.monitorenter: {
                }
                break;
                case Opcode.monitorexit: {
                }
                break;
                case Opcode.wide: {
                }
                break;
                case Opcode.multianewarray: {
                }
                break;
                case Opcode.ifnull: {
                }
                break;
                case Opcode.ifnonnull: {
                }
                break;
                case Opcode.goto_w: {
                }
                break;
                case Opcode.jsr_w: {
                }
                break;
                case Opcode.breakpoint: {
                }
                break;
                case Opcode.impdep1: {
                }
                break;
                case Opcode.impdep2: {
                }
                break;
            }
        }
    }

    /**
     * 解析字段
     * @param className
     * @param descriptorName
     * @param fieldName
     */
    private field_info parseFieldRef(String className, String descriptorName, String fieldName) {
       // MethodArea methodArea = runTimeEnv.methodArea;
        JavaClass javaClass = runTimeEnv.methodArea.loadClass(className);
        runTimeEnv.methodArea.linkClass(className);
        runTimeEnv.methodArea.initClass(className);
        JavaClass javaClass1 = runTimeEnv.methodArea.findClass(className);
        field_info field_info = javaClass1.findField(fieldName, descriptorName);
        return field_info;
    }



    private void invokeVirtual(JavaClass javaClass, CONSTANT_Base constant_base) {
        CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
        CONSTANT_Methodref methodref  = (CONSTANT_Methodref) constant_base;
        int class_index = TypeUtils.byteArr2Int(methodref.class_index.u2);
        int name_and_type_index = TypeUtils.byteArr2Int(methodref.name_and_type_index.u2);

        CONSTANT_Class constant_class = (CONSTANT_Class) constant_bases[class_index - 1];
        CONSTANT_NameAndType constant_nameAndType = (CONSTANT_NameAndType)constant_bases[name_and_type_index - 1];

        CONSTANT_Utf8 methodNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.name_index.u2) - 1];
        CONSTANT_Utf8 descriptorNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.descriptor_index.u2) - 1];
        CONSTANT_Utf8 classNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_class.name_index.u2) - 1];

        String methodName = TypeUtils.u12String(methodNameUtf8.bytes);
        String descriptorName = TypeUtils.u12String(descriptorNameUtf8.bytes);
        String className = TypeUtils.u12String(classNameUtf8.bytes);

        method_info method_info = parseMethodRef(className, descriptorName, methodName);
        /*调用传递参数*/
        Descriptor descriptor = processDescriptor(descriptorName);
        int parametersCount = descriptor.parameters.size();

        JObject jObject = jThread.getTopFrame().operandStack.getJObjectFromTop(method_info.argSlotCount - 1);

        if(jObject == null){
            /*hack*/
            if("println".equals(methodName)){
                _println(jThread.getTopFrame().operandStack, descriptorName);
                return;
            }
        }


    }

    private void _println(OperandStack operandStack, String descriptor) {
        if("(Z)V".equals(descriptor)){
            System.out.println(operandStack.popInt() != 0);
        }else if("(C)V".equals(descriptor)){
            System.out.println((char)operandStack.popInt());
        }else if("(I)V".equals(descriptor)
                ||"(B)V".equals(descriptor)||"(S)V".equals(descriptor)){
            System.out.println(operandStack.popInt());
        }else if("(F)V".equals(descriptor)){
            System.out.println(operandStack.popFloat());
        }else if("(J)V".equals(descriptor)){
            System.out.println(operandStack.popLong());
        }else if("(D)V".equals(descriptor)){
            System.out.println(operandStack.popDouble());
        }else {
            System.out.println("println " + descriptor);
        }
    }


    /**
     * 解析方法
     * @param className
     * @param descriptorName
     * @param methodName
     */
    private method_info parseMethodRef(String className, String descriptorName, String methodName) {
        JavaClass javaClass = runTimeEnv.methodArea.loadClass(className);
        runTimeEnv.methodArea.linkClass(className);
        runTimeEnv.methodArea.initClass(className);
        JavaClass javaClass1 = runTimeEnv.methodArea.findClass(className);
        method_info method_info = javaClass1.findMethod(methodName, descriptorName);
        if(method_info.argSlotCount == -1){
            Descriptor descriptor = processDescriptor(descriptorName);
            int slotCount = calParametersSlot(method_info, descriptor.parameters);
            method_info.argSlotCount = slotCount;

        }
        return method_info;
    }

    private void invokeStatic(JavaClass javaClass, CONSTANT_Base constant_base) {
        CONSTANT_Base[] constant_bases = javaClass.getClassFile().constant_pool.cp_info;
        CONSTANT_Methodref methodref  = (CONSTANT_Methodref) constant_base;
        int class_index = TypeUtils.byteArr2Int(methodref.class_index.u2);
        int name_and_type_index = TypeUtils.byteArr2Int(methodref.name_and_type_index.u2);

        CONSTANT_Class constant_class = (CONSTANT_Class) constant_bases[class_index - 1];
        CONSTANT_NameAndType constant_nameAndType = (CONSTANT_NameAndType)constant_bases[name_and_type_index - 1];

        CONSTANT_Utf8 methodNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.name_index.u2) - 1];
        CONSTANT_Utf8 descriptorNameUtf8 = (CONSTANT_Utf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.descriptor_index.u2) - 1];

        String methodName = TypeUtils.u12String(methodNameUtf8.bytes);
        String descriptorName = TypeUtils.u12String(descriptorNameUtf8.bytes);
       // method_info method_info = javaClass.findMethod(methodName, descriptorName);
        method_info method_info = javaClass.findMethod(methodName, descriptorName);
        if (method_info == null){
            return ;
        }
        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.max_stack, callSite.max_locals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        Descriptor descriptor = processDescriptor(descriptorName);

        int slotCount = calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }

        executeByteCode(jThread, javaClass, callSite.code, TypeUtils.byteArr2Int(callSite.code_length.u4));

    }

    /**
     * 解析方法的修饰符，如(J)J解析为Descriptor：{parameters:[J],returnType:J}
     * @param descriptorName
     * @return
     */
    private Descriptor processDescriptor(String descriptorName) {
        Descriptor descriptor = new Descriptor();
        List<Integer> parameters = new ArrayList<>();
        Integer returnType = new Integer(0);
        char[] descriptorNameArr = descriptorName.toCharArray();
        int i = 0;
        while (descriptorNameArr[i] != ')'){
            switch (descriptorNameArr[i]) {
                case 'B': {
                    parameters.add(TypeCode.T_BYTE);
                }
                break;
                case 'C': {
                    parameters.add(TypeCode.T_CHAR);
                }
                break;
                case 'D': {
                    parameters.add(TypeCode.T_DOUBLE);

                }
                break;
                case 'F': {
                    parameters.add(TypeCode.T_FLOAT);

                }
                break;
                case 'I': {
                    parameters.add(TypeCode.T_INT);

                }
                break;
                case 'J': {
                    parameters.add(TypeCode.T_LONG);

                }
                break;
                case 'S': {
                    parameters.add(TypeCode.T_SHORT);

                }
                break;
                case 'Z': {
                    parameters.add(TypeCode.T_BOOLEAN);

                }
                break;
                case '[': {
                    int arrayComponentType = ++i;
                    while (descriptorNameArr[arrayComponentType] == '[') {
                        arrayComponentType++;
                    }
                    i = arrayComponentType;
                    parameters.add(TypeCode.T_EXTRA_ARRAY);
                }
                break;
                case 'L': {
                    int objectType = i++;
                    while (descriptorNameArr[objectType] != ';') {
                        objectType++;
                    }
                    i = objectType;
                    parameters.add(TypeCode.T_EXTRA_OBJECT);
                }
                break;
            }
            i++;
        }
        int len = descriptorName.length();
        while (i < len) {
            switch (descriptorNameArr[i]) {
                case 'B':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'C':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'D':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'F':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'I':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'J':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'S':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'Z':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'V':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case '[': {
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'L': {
                    returnType = TypeCode.T_BYTE;
                }
                break;
            }
            i++;
        }
        descriptor.parameters = parameters;
        descriptor.returnType = returnType;
        return descriptor;
    }

    private int calParametersSlot(method_info method_info, List<Integer> parameters){
        int parametersCount = parameters.size();

        int slotCount = parametersCount;
        for(int i = 0; i < parametersCount; i++){
            if(parameters.get(i) == TypeCode.T_LONG
                    ||parameters.get(i) == TypeCode.T_DOUBLE){
                slotCount ++;
            }
        }
        if(!MethodArea.isStatic(method_info.access_flags)){
            slotCount ++;/*'this' 引用*/
        }
        return slotCount;
    }

    private JObject execNew(JavaClass javaClass,CONSTANT_Utf8 constant_utf8) {
        String str = TypeUtils.u12String(constant_utf8.bytes);
        return runTimeEnv.javaHeap.createJObject(javaClass);
    }

}
