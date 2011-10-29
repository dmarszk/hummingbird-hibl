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
	processor 0x2FFFFFE
	coprocessor 0x30F8

	org 0xD0020000
	align 4
c_start:
	file 'BL1_stage1.bin' ; must be signed for secure boot
	db 0x2000 - ($-c_start) dup 0 ; here starts our BL1_stage1 0xD0022000

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
	LDR  R0, [magic_determ_location]
	LDR  R0, [R0]
	LDR  R1, [infuse_magic]
	CMP  R0, R1
	BEQ  load_infuse
	LDR  R0, [sgs_ptrs_l]
	BL   load_ptrs
	B    ptrsloaded
load_infuse:
	LDR  R0, [infuse_ptrs_l]
	BL   load_ptrs
ptrsloaded:
	MOV  R0, 0xA
	BL   debug_print_byte
	LDR  R0, [s_detected_irom_a]
	BL   debug_print
	LDR  R0, [irom_rev_str_a]
	BL   debug_print

	MOV  R0, 0xA
	BL   debug_print_byte

	LDR  R0, [s_welcome_a]
	BL   debug_print

	MOV  R0, 0xA
	BL   debug_print_byte

	LDR  R0, [s_ibl_jumpout_a]
	BL   debug_print

	BL   jump_to_sgs_ibl
	LDR  R0, [s_done_a]
	BL   debug_print


	BL   init_system


	LDR  R0, [s_ram_test_a]
	BL   debug_print
	LDR  R0, [BL3_memblock]
	LDR  R1, [ram_test_magic1]
	STR  R1, [R0]
	LDR  R2, [R0]
	CMP  R1, R2
	BNE  fail
	LDR  R0, [s_done_a]
	BL   debug_print



	LDR  R0, [s_iram_init_a]
	BL   debug_print

	LDR	R0, [uart_reg]
	LDR	R6, [R0]

	LDR	R0, [mirror_copy_start]
	LDR	R1, [init_vars_start]
	LDR	R2, [init_vars_size]
	BL	rebell_memcpy

	LDR	R0, [uart_reg]
	STR	R6, [R0]

	LDR  R0, [s_done_a]
	BL   debug_print

	LDR  R0, [s_otg_clean_a]
	BL   debug_print
	LDR	R0, [otg_info]
	MOV	R1, 0
	MOV	R2, 0x128
	BL	rebell_fillmem

	LDR  R0, [s_done_a]
	BL   debug_print

	LDR  R0, [s_dl_start_a]
	BL   debug_print
	BL   start_usb_booting
	MOV  R3, R0
	BL   printhexint
	MOV  R0, R3
	CMP  R0, 0
	BNE  fail

	LDR  R0, [s_bl3_ep_a]
	BL   debug_print
	LDR  R0, [upload_ep_ptr]
	LDR  R0, [R0]
	BL   printhexint

	MOV  R0, 0xA
	BL   debug_print_byte

	LDR  R0, [s_bl3start_a]
	BL   debug_print
	LDR  R0, [upload_ep_ptr]
	LDR  R0, [R0]
	BX  R0



fail:
	LDR  R0, [s_failed_a]
	BL   debug_print
	MOV  R0, 3
	BL   countdown
endless_loop:
	b    endless_loop

s_welcome_a	dw s_welcome
s_bl3_ep_a	dw s_bl3_ep
s_ibl_jumpout_a dw s_ibl_jumpout
s_iram_init_a	dw s_iram_init
s_otg_clean_a	dw s_otg_clean
s_ram_test_a	dw s_ram_test
s_dl_start_a	dw s_dl_start
s_failed_a	dw s_failed
s_bl3start_a	dw s_bl3start
s_done_a	dw s_done
s_detected_irom_a dw s_detected_irom

s_welcome db 0xA,\
'-------------------------------------------------------------',0xA,\
'   Hummingbird Interceptor Boot Loader (HIBL) v2.0',0xA,\
'   Copyright (C) Rebellos 2011',0xA,\
'-------------------------------------------------------------',0xA,0x0

s_detected_irom db 'Detected iROM version: ',0x0
s_bl3_ep db 'BL3 EP: ',0x0
s_ibl_jumpout db 'Calling IBL Stage2',0x0
s_iram_init db 'iRAM reinit',0x0
s_otg_clean db 'cleaning OTG context',0x0
s_ram_test db 'Testing DRAM1',0x0
s_dl_start  db 'Chain of Trust has been successfully compromised.',0xA,0xA,'Begin unsecure download now...',0xA,0x0
s_failed  db 'FAILED! Phone will hang...',0xA,0x0
s_bl3start db 'Download complete, hold download mode key combination.',0xA,0xA,'Starting BL3 in...',0xA,0x0
s_done	  db ' ...OK',0xA,0x0
align 4


BL3_memblock	  dw 0x40200000
ram_test_magic1   dw 0x12349876

magic_determ_location	dw 0xD0000100

infuse_magic	  dw 0xE5925000
infuse_ptrs_l	  dw infuse_ptrs
sgs_magic	  dw 0x11A0F000
sgs_ptrs_l	  dw sgs_ptrs
irom_rev_str_a	  dw irom_rev_str

infuse_ptrs:
include 'infuse.inc'

sgs_ptrs:
include 'sgs.inc'


load_ptrs:
   STMFD SP!, {R1-R3,LR}
   MOV R1, 0
   ldr r2, [BL_abs_ptr]
copy_loop:
   ldr r3, [r0]
   str r3, [r2]
   add r0, r0, 4
   add r2, r2, 4
   add r1, r1, 4
   cmp r1, #bl_abs_st_len
   bcc copy_loop
   LDMFD SP!, {R1-R3,PC}

align 4
bl_abs_st:
irom_rev_str		db 32 dup 0
mirror_copy_start	dw 0
init_vars_size		dw 0
upload_ep_ptr		dw 0
init_vars_start 	dw 0
uart_reg		dw 0
otg_info		dw 0
v_init_system		dw 0
v_start_usb_booting	dw 0
v_system_pause		dw 0
v_debug_print_irom	dw 0
v_debug_print_byte_irom dw 0

bl_abs_st_end:
BL_abs_ptr dw bl_abs_st
bl_abs_st_len = bl_abs_st_end - bl_abs_st


   init_system: 	   ldr pc, [v_init_system]
   start_usb_booting:	   ldr pc, [v_start_usb_booting]
   system_pause:	   ldr pc, [v_system_pause]
   debug_print_irom:	   ldr pc, [v_debug_print_irom]
   debug_print_byte_irom:  ldr pc, [v_debug_print_byte_irom]


jump_to_sgs_ibl:
    STMFD SP!, {LR}
    B sgs_ibl_stage2
FUNCTIONS


align 4

db 0x4000 - ($-c_start) dup 0
sgs_ibl_stage2:
file 'init_by_rebell.bin'


db 0x6000 - ($-c_start) dup 0



