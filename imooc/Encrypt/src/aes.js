//data 是准备加密的字符串,key是你的密钥

var key = 'TS8/6mwD15uE8I1r8DQv0w==';
var keyByte = new Buffer(key, "base64");
var crypto = require('crypto');

function encryption(data, key) {
    var iv = "";
    var clearEncoding = 'utf8';
    var cipherEncoding = 'base64';
    var cipherChunks = [];
    console.log(Buffer.byteLength(data));
    var cipher = crypto.createCipheriv('aes-128-ecb', key, iv);
    cipher.setAutoPadding(true);

    cipherChunks.push(cipher.update(data, clearEncoding, cipherEncoding));
    cipherChunks.push(cipher.final(cipherEncoding));

    return cipherChunks.join('');
}
//data 是你的准备解密的字符串,key是你的密钥
function decryption(data, key) {
    var iv = "";
    var clearEncoding = 'utf8';
    var cipherEncoding = 'base64';
    var cipherChunks = [];
    var decipher = crypto.createDecipheriv('aes-128-ecb', key, iv);
    decipher.setAutoPadding(true);

    cipherChunks.push(decipher.update(data, cipherEncoding, clearEncoding));
    cipherChunks.push(decipher.final(clearEncoding));

    return cipherChunks.join('');
}

var clearText = '中文tingkl';
// elu4pRSBQ05Ormx6VEek2w==
var b = encryption(clearText, keyByte);
var a = decryption(b, keyByte);
console.log(a, b);
/*
 hexKey:4d2f3fea6c03d79b84f08d6bf0342fd3
 base64Key:TS8/6mwD15uE8I1r8DQv0w==
 aes encrypt:Bkj/8oi+AazDFK19BzjESQ==
             Bkj/8oi+AazDFK19BzjESQ==
 aes decrypt:中文tingkl
*/

// 3vHb+dAoz0jZ5ZvRBiDWQg==