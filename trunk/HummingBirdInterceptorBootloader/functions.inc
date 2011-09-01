;   Hummingbird Interceptor Boot Loader (HIBL)
;
;   Copyright 2011 Dominik Marszk
;
;   Licensed under the Apache License, Version 2.0 (the "License");
;   you may not use this file except in compliance with the License.
;   You may obtain a copy of the License at
;
;       http://www.apache.org/licenses/LICENSE-2.0
;
;   Unless required by applicable law or agreed to in writing, software
;   distributed under the License is distributed on an "AS IS" BASIS,
;   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;   See the License for the specific language governing permissions and
;   limitations under the License.

macro FUNCTIONS
{
;int rebell_strlen(char* str)
rebell_strlen:
	STMFD	SP!, {R1-R4,LR\}
	MOV	R1, 0
rebell_strlen_loop:
	LDRB	R2, [R0], 1
	CMP	R2, 0x0
	BEQ	rebell_strlen_ret
	ADD	R1, R1, 1
	B	rebell_strlen_loop
rebell_strlen_ret:
	MOV	R0, R1
	LDMFD	SP!, {R1-R4,PC\}


;countdown, 1-9
countdown:
    STMFD SP!, {R2-R4, LR\}
    MOV   R4, R0
    ADD   R4, 48
count_loop:
    MOV   R0, R4
    BL	  debug_print_byte
    MOV   R0, 0xA
    BL	  debug_print_byte
    MOV   R1, 1000
    MOV   R0, 1
    BL	  system_pause
    SUB   R4, R4, 1
    CMP   R4, 48
    BGE   count_loop
    LDMFD SP!, {R2-R4, PC\}

;printhexint(int i)
printhexint:
	STMFD	SP!, {R1-R4,LR\}
	MOV	R4, R0
	MOV	R3, 0
	MOV	R0, '0';
	BL	debug_print_byte
	MOV	R0, 'x';
	BL	debug_print_byte
	UBFX	R0, R4, 28, 4
	BL	printhexchar
	UBFX	R0, R4, 24, 4
	BL	printhexchar
	UBFX	R0, R4, 20, 4
	BL	printhexchar
	UBFX	R0, R4, 16, 4
	BL	printhexchar
	UBFX	R0, R4, 12, 4
	BL	printhexchar
	UBFX	R0, R4, 8, 4
	BL	printhexchar
	UBFX	R0, R4, 4, 4
	BL	printhexchar
	UBFX	R0, R4, 0, 4
	BL	printhexchar
	LDMFD	SP!, {R1-R4,PC\}

;printhexchar(4bit c)
printhexchar:
	STMFD	SP!, {LR\}
	CMP	R0, 10
	ADDCC	R0, R0, 48
	ADDCS	R0, R0, (65-10)
	BL	debug_print_byte
	LDMFD	SP!, {PC\}

debug_print:
	STMFD	SP!, {R1-R4,LR\}
	BL	debug_print_irom
	LDMFD	SP!, {R1-R4,PC\}

debug_print_byte:
	STMFD	SP!, {R1-R4,LR\}
	BL	debug_print_byte_irom
	LDMFD	SP!, {R1-R4,PC\}
;void rebell_fillmem(R0 void* ptr, R1 byte fillbyte, R2 int length)
rebell_fillmem:
	STMFD	SP!, {R3,LR\}
	MOV	R3, 0
rebell_fillmem_loop:
	CMP	R3, R2
	BGE	rebell_fillmem_ret
	STRB	R1, [R0, R3]
	ADD	R3, R3, 1
	B	rebell_fillmem_loop
rebell_fillmem_ret:
	LDMFD	SP!, {R3,PC\}
;be aware of code below, need someone to check and fix it
;it is 100% copy->paste from PBL with commented out 0x40 mapping
configure_ram:
	STMFD	SP!, {R0-R5,LR\}
	LDR	R0, [APLL_LOCK]
	ORR	R0, R0, #0x6200
	ORR	R0, R0, #8
	MOV	R1, #0
	STR	R1, [R0]
	LDR	R0, [RST_STAT]
	LDR	R3, [R0]
	AND	R3, R3, #0x10000
	LDR	R0, [ASYNC_MSYS_DMC0]
	MOV	R1, #0
	STR	R1, [R0]
	MOV	R1, #0
	STR	R1, [R0,#0xC]
	LDR	R0, [DMC0_REG]
	LDR	R1, [conf_0x101000]
	STR	R1, [R0,#0x18]
	MOV	R1, #0x84
	STR	R1, [R0,#0x1C]
	LDR	R1, [conf_0x101000]
	ADD	R1, R1, 2
	STR	R1, [R0,#0x18]
	LDR	R1, [conf_0x101000]
	ADD	R1, R1, 3
	STR	R1, [R0,#0x18]
	MOV	R2, #0x4000
loopconfram:
	SUBS	R2, R2, #1
	CMP	R2, #0
	BNE	loopconfram
	LDR	R1, [conf_0x60101003]
	STR	R1, [R0,#0x18]
	CMP	R3, #0x10000
	BNE	configram2
	LDR	R1, [conf_0x60101001]
	STR	R1, [R0,#0x18]
configram2:
	LDR	R1, [conf_0xFFF1010]
	STR	R1, [R0]
	LDR	R1, [conf_0x212100]
	STR	R1, [R0,#4]
	LDR	R1, [conf_0x30F82222]
	STR	R1, [R0,#8]
	LDR	R1, [conf_0x40F02222]
	STR	R1, [R0,#0xC]
	MOV	R1, #0xFF000000
	STR	R1, [R0,#0x14]
	MOV	R1, 0x50E
	STR	R1, [R0,#0x30]
	LDR	R1, [conf_0x14233287]
	STR	R1, [R0,#0x34]
	LDR	R1, [conf_0x12130005]
	STR	R1, [R0,#0x38]
	LDR	R1, [conf_0xE140222]
	STR	R1, [R0,#0x3C]
	MOV	R1, #0x7000000
	STR	R1, [R0,#0x10]
	MOV	R1, #0x1000000
	STR	R1, [R0,#0x10]
	MOV	R1, #0x5000000
	STR	R1, [R0,#0x10]
	MOV	R1, #0x5000000
	STR	R1, [R0,#0x10]
	MOV	R1, #0x32
	STR	R1, [R0,#0x10]
	LDR	R1, [conf_0x20020]
	STR	R1, [R0,#0x10]
	MOV	R1, #0x7100000
	STR	R1, [R0,#0x10]
	MOV	R1, #0x1100000
	STR	R1, [R0,#0x10]
	MOV	R1, #0x5100000
	STR	R1, [R0,#0x10]
	MOV	R1, #0x5100000
	STR	R1, [R0,#0x10]
	LDR	R1, [conf_0x100032]
	STR	R1, [R0,#0x10]
	LDR	R1, [conf_0x120020]
	STR	R1, [R0,#0x10]
	LDR	R1, [conf_0xFFF10B0]
	STR	R1, [R0]
	MOV	R1, 0xFFFF00FF
	STR	R1, [R0,#0x28]
	LDR	R1, [conf_0x212113]
	STR	R1, [R0,#4]
	LDR	R0, [DMC1_REG] ; DMC1_config
	LDR	R1, [conf_0x101000]
	STR	R1, [R0,#0x18]
	MOV	R1, #0x84
	STR	R1, [R0,#0x1C]
	LDR	R1, [conf_0x101000]
	ADD	R1, R1, 2
	STR	R1, [R0,#0x18]
	LDR	R1, [conf_0x101000]
	ADD	R1, R1, 3
	STR	R1, [R0,#0x18]
	MOV	R2, #0x4000
loopconfram2:
	SUBS	R2, R2, #1
	CMP	R2, #0
BNE	loopconfram2
	LDR	R1, [conf_0x50101003]
	STR	R1, [R0,#0x18]
	CMP	R3, #0x10000
BNE	configram3
	LDR	R1, [conf_0x50101001]
	STR	R1, [R0,#0x18]
configram3:
;LDR     R0, [DMC1_REG]
;LDR     R1, [conf_0xFFF1010]
;STR     R1, [R0]
;LDR     R1, [conf_0x212100]
	;MOV     R5, R0
	;MOV     R0, 9999
	;BL      int_debugprint
       ; MOV     R0, R5
;STR     R1, [R0,#4]

	;MOV     R0, 9999
	;BL      int_debugprint
	LDMFD	SP!, {R0-R5,PC\}
;endproc

	conf_0xE2700000  dw 0xE2700000
	conf_0xE2900C00  dw 0xE2900C00
	conf_0x120020	 dw 0x120020
	conf_0xFFF10B0	 dw 0xFFF10B0
	conf_0x212113	 dw 0x212113
	conf_0x50101003  dw 0x50101003
	conf_0x50101001  dw 0x50101001
	conf_0xFFF1010	 dw 0xFFF1010
	APLL_LOCK	 dw 0xE0100000
	RST_STAT	 dw 0xE010A000
	ASYNC_MSYS_DMC0  dw 0xF1E00000
	DMC0_REG	 dw 0xF0000000
	conf_0x60101003  dw 0x60101003
	conf_0x60101001  dw 0x60101001
	conf_0x101000	 dw 0x101000
	DMC1_REG	 dw 0xF1400000
	conf_0x40F02222  dw 0x40F02222
	conf_0x212100	 dw 0x212100
	conf_0x30F82222  dw 0x30F82222
	conf_0x14233287  dw 0x14233287
	conf_0x12130005  dw 0x12130005
	conf_0xE140222	 dw 0xE140222
	conf_0x20020	 dw 0x20020
	conf_0x100032	 dw 0x100032

;void rebell_memcpy(int source(r0), int target(r1), int size(r2))
;returns num of bytes copied (always equal to size or just hangs on mem access violation)
rebell_memcpy:
	STMFD	SP!, {R3-R6,LR\}

	MOV	R3, R2
	MOV	R2, R1
	MOV	R1, R0

	MOV	R0, R1
	MOV	R1, R2
	MOV	R2, R3

	MOV	R3, 0
rebell_memcpy_copyloop:
	ldrb	r4, [r0,r3] ;src
	strb	r4, [r1,r3] ;dst
	add	r3, r3, 1
	cmp	r3, r2
	BLT	rebell_memcpy_copyloop
	MOV	R0, R2
	LDMFD	SP!, {R3-R6,PC\}


;;Rewritten SBL functions to configure platform and CPU like SBL does
configure_uart:
	LDR	R4, [UART2_PTR]
	MOV	R3, #0x23
	STRH	R3, [SP,#8]
	MOV	R3, #0x80
	STRH	R3, [SP,#6]
	LDR	R3, [R4, #4]
	BIC	R3, R3, #0xF
	STR	R3, [R4, #4]
	MOV	R3, #0
	STR	R3, [R4, #8]
	MOV	R3, #0
	STR	R3, [R4, #0xC]
	MOV	R3, #3
	STR	R3, [R4]
	MOV	R3, #0x240
	STR	R3, [R4, 4]
	LDRH	R3, [SP,#8]
	STR	R3, [R4, 0x28]
	LDRH	R3, [SP,#6]
	STR	R3, [R4, 0x2C]
	LDR	R3, [R4, 4]
	ORR	R3, R3, #5
	STR	R3, [R4, 4]
	BX	LR
configure_clocks:
	LDR	R3, [CLK_DIV1]
	LDR	R3, [R3]
	BIC	R3, R3, #0xF00000
	ORR	R3, R3, #0x300000
	LDR	R2, [CLK_DIV1]
	STR	R3, [R2]
	LDR	R3, [CLK_DIV0]
	LDR	R3, [R3]
	AND	R3, R3, #0x80000000
	MOV	R2, R3
	LDR	R3, [clk_div_mask]
	ORR	R3, R2, R3
	LDR	R2, [CLK_DIV0]
	STR	R3, [R2]

	wait_for_stable_div:
	LDR	R3, [CLK_DIV_STAT0]
	LDR	R3, [R3]
	AND	R3, R3, #1
	CMP	R3, #0
	BNE	wait_for_stable_div

	LDR	R3, [DMC0_REG]
	LDR	R3, [R3, 0x18]
	BIC	R3, R3, #2
	LDR	R2, [DMC0_REG]
	STR	R3, [R2, 0x18]
	LDR	R3, [DMC1_REG]
	LDR	R3, [R3, 0x18]
	BIC	R3, R3, #2
	LDR	R2, [DMC1_REG]
	STR	R3, [R2, 0x18]


	LDR	R2, [CLK_GATE_IP0]
	LDR	R3, [CLK_GATE_IP0]
	LDR	R3, [R3]
	BIC	R3, R3, #0x80000000
	STR	R3, [R2]
	LDR	R2, [CLK_GATE_IP1]
	LDR	R3, [CLK_GATE_IP1]
	LDR	R3, [R3]
	BIC	R3, R3, #0x10000000
	STR	R3, [R2]
	LDR	R2, [CLK_GATE_IP2]
	LDR	R3, [CLK_GATE_IP2]
	LDR	R3, [R3]
	BIC	R3, R3, #0x200
	BIC	R3, R3, #2
	STR	R3, [R2]
	LDR	R2, [CLK_GATE_IP3]
	LDR	R3, [CLK_GATE_IP3]
	LDR	R3, [R3]
	BIC	R3, R3, #3
	STR	R3, [R2]
	LDR	R2, [CLK_GATE_IP4]
	LDR	R3, [CLK_GATE_IP4]
	LDR	R3, [R3]
	BIC	R3, R3, #6
	STR	R3, [R2]
	BX	LR

    UART2_PTR		dw 0xE2900800
    clk_div_mask	dw 0x14131330
    CLK_DIV0		dw 0xE0100300
    CLK_DIV1		dw 0xE0100304
    CLK_DIV2		dw 0xE0100308
    CLK_DIV3		dw 0xE010030C
    CLK_DIV4		dw 0xE0100310
    CLK_DIV5		dw 0xE0100314
    CLK_DIV6		dw 0xE0100318
    CLK_DIV7		dw 0xE010031C
    CLK_GATE_IP0	dw 0xE0100460
    CLK_GATE_IP1	dw 0xE0100464
    CLK_GATE_IP2	dw 0xE0100468
    CLK_GATE_IP3	dw 0xE010046C
    CLK_GATE_IP4	dw 0xE0100470
    CLK_DIV_STAT0	dw 0xE0101000

timer_driver:
	var_18= -0x18
	var_4= -4
	STR	R11, [SP,#var_4]!
	ADD	R11, SP, #4+var_4
	SUB	SP, SP, #0xC
	LDR	R2, [TCFG0]
	MOV	R3, #0xF00
	STR	R3, [R2]
	LDR	R3, [TCFG1]
	LDR	R3, [R3]
	BIC	R3, R3, #0xF0000
	STR	R3, [R11,#0x10+var_18]
	LDR	R2, [TCFG1]
	LDR	R3, [R11,#0x10+var_18]
	ORR	R3, R3, #0x10000
	STR	R3, [R2]
	LDR	R2, [TCNTB4]
	MOV	R3, 0xFFFFFFFF
	STR	R3, [R2]
	LDR	R2, [TCON]
	LDR	R3, [TCON]
	LDR	R3, [R3]
	BIC	R3, R3, #0x700000
	ORR	R3, R3, #0x600000
	STR	R3, [R2]
	LDR	R2, [TCON]
	LDR	R3, [TCON]
	LDR	R3, [R3]
	BIC	R3, R3, #0x700000
	ORR	R3, R3, #0x500000
	STR	R3, [R2]
	MOV	SP, R11
	LDMFD	SP!, {R11\}
	BX	LR

	TCFG0	dw  0xE2500000
	TCFG1	dw  0xE2500004
	TCON	dw  0xE2500008

	TCNTB4	dw  0xE250003C




}