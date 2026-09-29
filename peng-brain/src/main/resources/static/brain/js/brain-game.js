/**
 * 智域大脑 · 游戏页公共脚本
 * 提供成绩保存、公共工具函数
 */
var BrainGame = {
	/**
	 * 保存成绩到后端(用户ID由后端取当前登录用户)
	 * @param opts {gameType, testType, score, duration, level, extra, onSuccess, onError}
	 */
	saveScore: function (opts) {
		var csrftoken = $('meta[name=csrf-token]').attr('content');
		$.ajax({
			url: ctx + 'brain/test/save',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({
				gameType: opts.gameType,
				testType: opts.testType,
				score: opts.score,
				duration: opts.duration,
				level: opts.level || 1,
				extra: opts.extra || ''
			}),
			beforeSend: function (xhr) {
				if (csrftoken) { xhr.setRequestHeader('X-CSRF-Token', csrftoken); }
			},
			success: function (res) {
				if (res && res.code === 0) {
					opts.onSuccess && opts.onSuccess(res);
				} else {
					if (opts.onError) {
						opts.onError(res);
					} else if (res && res.msg) {
						// 未登录等场景给出提示(不中断游戏流程)
						layer.msg(res.msg, { icon: 0, time: 2000 });
					}
				}
			},
			error: function (xhr, status, err) {
				if (opts.onError) {
					opts.onError({ msg: status + ': ' + err });
				} else {
					layer.msg('成绩保存失败: ' + status, { icon: 2, time: 2000 });
				}
			}
		});
	},

	/** 打乱数组 */
	shuffle: function (arr) {
		for (var i = arr.length - 1; i > 0; i--) {
			var j = Math.floor(Math.random() * (i + 1));
			var t = arr[i]; arr[i] = arr[j]; arr[j] = t;
		}
		return arr;
	},

	/** 秒 -> mm:ss */
	formatTime: function (seconds) {
		var m = Math.floor(seconds / 60);
		var s = Math.floor(seconds % 60);
		return (m < 10 ? '0' : '') + m + ':' + (s < 10 ? '0' : '') + s;
	}
};
