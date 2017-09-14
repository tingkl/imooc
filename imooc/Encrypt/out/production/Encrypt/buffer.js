function fillSmallThan128() {
    var buf = new Buffer(20);
    buf.fill('a');
    console.log(buf);
    console.log(Buffer.byteLength(buf));
    console.log(buf.toString());
}
fillSmallThan128();


function fillZW() {
    var buf = new Buffer(20);
    // 一个中文三个字节，剩下两个字节缺少一个字节，utf编码不了为乱码
    buf.fill('丁');
    console.log(buf);
    console.log(Buffer.byteLength(buf));
    console.log(buf.toString());
}
fillZW();

// utf-8 与 ascii码一致
function smallThan128() {
    var str = "abcdefjhijklmn";
    for (var i = 0; i < str.length; i++) {
        console.log(new Buffer(str[i]));
    }
    /*
     <Buffer 61> 16进制， 97
     <Buffer 62>         98
     <Buffer 63>
     <Buffer 64>
     <Buffer 65>
     <Buffer 66>
     <Buffer 6a>
     <Buffer 68>
     <Buffer 69>
     <Buffer 6a>
     <Buffer 6b>
     <Buffer 6c>
     <Buffer 6d>
     <Buffer 6e>
     * */
}
smallThan128();


function zw() {
    var str = "丁国梁";
    for (var i = 0; i < str.length; i++) {
        console.log(new Buffer(str[i]));
    }
    var d = new Buffer([0xe4, 0xb8, 0x81]);
    console.log(d.toString("utf-8"), d.toString(), d.toString("hex"));
    /*
     <Buffer e4 b8 81>
     <Buffer e5 9b bd>
     <Buffer e6 a2 81>
     -72
     -127
     -27
     -101
     -67
     -26
     -94
     -127
     * */
}
zw();