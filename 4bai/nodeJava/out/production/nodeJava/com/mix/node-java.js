/**
 * Created by tingkl on 2017/3/13.
 */
var cp = require('child_process');
var me = {
    encrypt: function (content, key, cb) {
        cp.exec('java -jar ./java.jar encrypt ' + key + ' ' + content, {
            maxBuffer: 1024 * 1024
        }, function (err, stdout, stderr) {
            // 当子进程执行完，才会执行
            console.log(stdout, stderr);
            cb && cb(stderr || err, stdout);
        });
    },
    decrypt: function (content, key, cb) {
        cp.exec('java -jar ./java.jar decrypt ' + key + ' ' + content, {
            maxBuffer: 1024 * 1024
        }, function (err, stdout, stderr) {
            // 当子进程执行完，才会执行
            console.log(stdout, stderr);
            cb && cb(stderr || err, stdout);
        });
    }
};
module.exports = me;

var key  = "jP9xl59o";
var date = Date.now();
me.encrypt("18817350807", key, function (err, result) {
    result = JSON.parse(result);
    console.log(result.success, result.msg)
    console.log(Date.now() - date);
});
me.decrypt("e65d37b6767a3901ff2b3b814b2557ca", key, function (err, result) {
    result = JSON.parse(result);
    console.log(result.success, result.msg)
});
// e65d37b6767a3901ff2b3b814b2557ca