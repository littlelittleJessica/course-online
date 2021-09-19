Tool = {
    /**
     * 空校验 null或""都返回true
     */
    isEmpty: function (obj) {
        if ((typeof obj == 'string')) {
            return !obj || obj.replace(/\s+/g, "") == ""
        } else {
            return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0);
        }
    },

    /**
     * 非空校验
     */
    isNotEmpty: function (obj) {
        return !this.isEmpty(obj);
    },

    /**
     * 长度校验
     */
    isLength: function (str, min, max) {
        return $.trim(str).length >= min && $.trim(str).length <= max;
    },

    /**
     * 时间格式化，date为空时取当前时间
     */
    dateFormat: function (format, date) {
        let result;
        if (!date) {
            date = new Date();
        }
        const option = {
            "y+": date.getFullYear().toString(),        // 年
            "M+": (date.getMonth() + 1).toString(),     // 月
            "d+": date.getDate().toString(),            // 日
            "h+": date.getHours().toString(),           // 时
            "m+": date.getMinutes().toString(),         // 分
            "s+": date.getSeconds().toString()          // 秒
        };
        for (let i in option) {
            result = new RegExp("(" + i + ")").exec(format);
            if (result) {
                format = format.replace(result[1], (result[1].length == 1) ? (option[i]) : (option[i].padStart(result[1].length, "0")))
            }
        }
        return format;
    },

    /**
     * 移除对象数组中的对象
     * @param array
     * @param obj
     * @returns {number}
     */
    removeObj: function (array, obj) {
        let index = -1;
        for (let i = 0; i < array.length; i++) {
            if (array[i] === obj) {
                array.splice(i, 1);
                index = i;
                break;
            }
        }
        return index;
    },

    /**
     * 10进制转62进制
     * @param number
     * @returns {string}
     * @private
     */
    _10to62: function (number) {
        let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ';
        let radix = chars.length;
        let arr = [];
        do {
            let mod = number % radix;
            number = (number - mod) / radix;
            arr.unshift(chars[mod]);
        } while (number);
        return arr.join('');
    },

    /**
     * 保存登录用户信息
     */
    setLoginMember: function (loginMember) {
        SessionStorage.set(SESSION_KEY_LOGIN_MEMBER, loginMember);
    },

    /**
     * 获取登录用户信息
     */
    getLoginMember: function () {
        return SessionStorage.get(SESSION_KEY_LOGIN_MEMBER) || {};
    },

    /**
     * 随机生成[len]长度的[radix]进制数
     * @param len
     * @param radix 默认62
     * @returns {string}
     */
    uuid: function (len, radix) {
        let chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        let uuid = [];
        radix = radix || chars.length;
        for (let i = 0; i < len; i++) {
            uuid[i] = chars[0 | Math.random() * radix];
        }
        return uuid.join('');
    },

    /**
     * 对象数组按key对应的值排序
     * @param array sections=[{id:"aaa", sort=2},{id:"bbb", sort=1}]
     * @param key sort 按sort排序
     * @returns {*} 结果：sections=[{id:"bbb", sort=1},{id:"aaa", sort=2,}]
     */
    sortAsc: function (array, key) {
        return array.sort(function (obj1, obj2) {
            let val1 = obj1[key];
            let val2 = obj2[key];
            if (val1 < val2) {
                return -1;
            } else if (val1 > val2) {
                return 1;
            } else {
                return 0;
            }
        });
    }
};