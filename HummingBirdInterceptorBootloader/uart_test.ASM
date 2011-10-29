;   Hummingbird Interceptor Boot Loader (HIBL) v1.0
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

	format binary as 'bin'
	include 'functions.inc'
	include 'irom_addresses.inc'
	processor 0x2FFFFFE
	coprocessor 0x30F8

	org 0xD0020000
	align 4
c_start:
	file 'BL1_stage1.bin' ; must be signed for secure boot
fillup1:
	db 0x2000 - (fillup1-c_start) dup 0 ; here starts our BL1_stage1 0xD0022000

	dw 0x0 ; secure boot header - we are out of secure boot already, thanks to Samsung for signing BL1_stage1 which drops secure boot
	dw 0x0
	dw 0x0
	dw 0x0

	B	StartUp
;ARM core jump vector table
_undefined_instruction:
	b _undefined_instruction
_software_interrupt:
	b _software_interrupt
_prefetch_abort:
	b _prefetch_abort
_data_abort:
	b _data_abort
_not_used:
	b _not_used
_irq:
	b _irq
_fiq:
	b _fiq
;endof ARM core handlers

StartUp:

    ldr R1, [UART2]
    ADR R0, s_test1
    LDRb R0, [R0]
    STRb R0, [R1, 0x20]
    ADR R0, s_test2
    LDRb R0, [R0]
    STRb R0, [R1, 0x20]
    bl pause

    ldr R1, [UART0]
    ADR R0, s_test1
    LDRb R0, [R0]
    STRb R0, [R1, 0x20]
    ADR R0, s_test2
    LDRb R0, [R0]
    STRb R0, [R1, 0x20]
    bl pause
    ldr R1, [UART1]
    ADR R0, s_test1
    LDRb R0, [R0]
    STRb R0, [R1, 0x20]
    ADR R0, s_test2
    LDRb R0, [R0]
    STRb R0, [R1, 0x20]
    bl pause


    b StartUp

    pause:
    mov r0, 0
    pause_loop:
    add r0, r0, 1
    cmp r0, 0x1000
    BCC pause_loop
    MOV PC, LR

    UART0 dw 0xE2900000
    UART1 dw 0xE2900400
    UART2 dw 0xE2900800

    s_test1 db 'h',0
    align 4
    s_test2 db 'y',0
    align 4




jump_to_sgs_ibl:
    STMFD SP!, {LR}
    B sgs_ibl_stage2
FUNCTIONS


align 4

fillup2:
db 0x4000 - (fillup2-c_start) dup 0
sgs_ibl_stage2:
file 'init_by_rebell.bin'


fillup4:
db 0x6000 - (fillup4-c_start) dup 0



