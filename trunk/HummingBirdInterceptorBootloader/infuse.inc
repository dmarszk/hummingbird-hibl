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


@@:
db 'iROM rev 2.0 (Infuse 4G)'
db 32 - ($-@r) dup 0

 dw 0xD000DF80	   ;mirror_copy_start
 dw 0xB4	   ;init_vars_size
 dw 0xD0035514	   ;upload_ep_ptr
 dw 0xD0035400	   ;init_vars_start
 dw 0xD0035458	   ;uart_reg
 dw 0xD00354B4	   ;otg_info

 dw 0xD0007E14	   ;init_system
 dw 0xD0007EE0	   ;start_usb_booting
 dw 0xD000546C	   ;system_pause
 dw 0xD0005648	   ;debug_print_irom
 dw 0xD00055DC	   ;debug_print_byte_irom
