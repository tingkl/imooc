
var crypto = require('crypto');
//data 是准备加密的字符串,key是你的密钥
function encryption(data, key, cb) {
    var iv = "";
    var clearEncoding = 'utf8';
    var cipherEncoding = 'base64';
    var cipherChunks = [];
    var cipher = crypto.createCipheriv('des-ecb', key, iv);
    cipher.setAutoPadding(true);

    cipherChunks.push(cipher.update(data, clearEncoding, cipherEncoding));
    cipherChunks.push(cipher.final(cipherEncoding));

    cb && cb(cipherChunks.join(''));
    // return cipherChunks.join('');
}
//data 是你的准备解密的字符串,key是你的密钥
function decryption(data, key, cb) {
    var iv = "";
    var clearEncoding = 'utf8';
    var cipherEncoding = 'base64';
    var cipherChunks = [];
    var decipher = crypto.createDecipheriv('des-ecb', key, iv);
    decipher.setAutoPadding(true);

    cipherChunks.push(decipher.update(data, cipherEncoding, clearEncoding));
    cipherChunks.push(decipher.final(clearEncoding));
    console.log('decryption data: ', data, cipherChunks)

    // return cipherChunks.join('');
    cb && cb(cipherChunks.join(''));
}

var key = new Buffer('jP9xl59o', 'base64');
// var key = 'jP9xl59o';
var content = encryption('扯淡了', key);
console.log(content);
// decryption(content, key)
// e65d37b6767a3901ff2b3b814b2557ca

/*
*
* 1234567890123456789012
 16
 mYvJlctzSBnJyVkc0dhaIw
 123abc中文22

 * */

 module.exports = {
    encrypt: encryption,
    decrypt: decryption
 }