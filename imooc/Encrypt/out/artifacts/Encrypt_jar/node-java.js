/**
 * Created by tingkl on 2017/3/13.
 */
var cp = require('child_process');
var me = {
    encrypt: function (content, cb) {
        cp.exec('java -jar ./Encrypt.jar encrypt "' + content + '"', {
            maxBuffer: 1024 * 1024
        }, function (err, stdout, stderr) {
            // 当子进程执行完，才会执行
            console.log(stdout, stderr);
            cb && cb(stderr || err, stdout);
        });
    },
    decrypt: function (content, cb) {
        cp.exec('java -jar ./Encrypt.jar decrypt "' + content + '"', {
            maxBuffer: 1024 * 1024
        }, function (err, stdout, stderr) {
            // 当子进程执行完，才会执行
            console.log(stdout, stderr);
            cb && cb(stderr || err, stdout);
        });
    }
};
module.exports = me;

var date = Date.now();
var jsonOBJ = {
    a: 1,
    b: 'abcdefg',
    c: '中文',
    d: '首选项'
};
var jsonString = JSON.stringify(jsonOBJ);
var jsonBase64 = new Buffer(jsonString).toString("base64");
// console.log(jsonBase64);
me.encrypt(jsonBase64, function (err, result) {
    result = JSON.parse(result);
    me.decrypt(result.msg, function (err, result) {
        result = JSON.parse(result);
        // console.log(result.msg);
        var msg = new Buffer(result.msg, "base64").toString()
        console.log(JSON.parse(msg));
    });
});

// e65d37b6767a3901ff2b3b814b2557ca