#!/usr/bin/expect
set networkname [lindex $argv 0]
set password [lindex $argv 1]
set dns1 [lindex $argv 2]
set dns2 [lindex $argv 3]
if {$argc<3} {
  puts "参数不足"
  exit
} elseif {$argc==3} {
    if {$dns1=="empty"} {
        spawn sudo networksetup -setdnsservers  $networkname empty
    } else {
        spawn sudo networksetup -setdnsservers  $networkname $dns1
    }
} else { 
    spawn sudo networksetup -setdnsservers $networkname $dns1 $dns2
}
expect "Password"
send "$password\r\n"
expect eof
spawn  dscacheutil -flushcache
expect eof
spawn networksetup -getdnsservers $networkname
expect eof
