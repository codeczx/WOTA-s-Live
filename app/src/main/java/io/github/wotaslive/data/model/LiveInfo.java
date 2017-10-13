package io.github.wotaslive.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by codeczx on 2017/10/10.
 */

public class LiveInfo {

	/**
	 * status : 200
	 * message : 请求成功
	 * content : {"liveList":[{"liveId":"59de13f10cf294bc616de87e","title":"吕梦莹的直播间","subTitle":"日常写作业🐷","picPath":"/mediasource/live/15061690159861h5h22LAZ0.jpg","startTime":1507726321749,"memberId":286982,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59de13f10cf294bc616de87e.lrc","streamPath":"http://2519.liveplay.myqcloud.com/live/2519_3145421.flv","screenMode":0},{"liveId":"59de0c780cf294bc616de87c","title":"陈珂的电台","subTitle":"走近珂学，天气渐渐转凉❤️吃点什么好呢","picPath":"/mediasource/live/15077244071930u8O07aHfs.jpg,/mediasource/live/1507724407319mu25ZhE6HG.jpg,/mediasource/live/1507724407402h0eyfoQM6K.jpg,/mediasource/live/1507724407500xZXPo2KTNc.jpg,/mediasource/live/1507724407617yr1648O022.jpg,/mediasource/live/1507724407713CinPuJQMbq.jpg,/mediasource/live/1507724407818kkQGj03zqP.jpg,/mediasource/live/1507724407988opXFUU1WIu.jpg,/mediasource/live/1507724408112025O19z30i.jpg,/mediasource/live/1507724408308rXJYXXzc8D.jpg","startTime":1507724408463,"memberId":63548,"liveType":2,"picLoopTime":30000,"lrcPath":"/mediasource/live/lrc/59de0c780cf294bc616de87c.lrc","streamPath":"http://play.bcelive.com/live/lss-ge5e1i94x6ziwbnm.flv","screenMode":0},{"liveId":"59de06fd0cf264df95c2a726","title":"乔钰珂的直播间","subTitle":"我来了","picPath":"/mediasource/live/1507723004858b9qbRpO2ZP.jpg","startTime":1507723004983,"memberId":528339,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59de06fd0cf264df95c2a726.lrc","streamPath":"http://2519.liveplay.myqcloud.com/live/2519_3145077.flv","screenMode":0},{"liveId":"59de02d90cf2754cb09226fc","title":"刘力菲的直播间","subTitle":"嘻嘻","picPath":"/mediasource/avatar/327567gFa9N4GB70.jpg","startTime":1507721943866,"memberId":327567,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59de02d90cf2754cb09226fc.lrc","streamPath":"http://pl.live.weibo.com/alicdn/1a94541079112c26d867c0994fe4f5a5_wb480.flv","screenMode":0}],"reviewList":[{"liveId":"59ddfa9b0cf294bc616de879","title":"赵佳蕊的电台","subTitle":"噜噜噜","picPath":"/mediasource/live/1507719835105mVEfM7BOzW.png,/mediasource/live/15066074644142YgM2L6iYX.jpg,/mediasource/live/1506607464545FP48XW2S8x.jpg,/mediasource/live/150660746466600wUiv81Hj.jpg,/mediasource/live/1506607464841KhVZH2oNHf.jpg,/mediasource/live/1506607465055Y4K6MR8y1B.jpg","startTime":1507719835244,"memberId":460005,"liveType":2,"picLoopTime":30000,"lrcPath":"/mediasource/live/lrc/59ddfa9b0cf294bc616de879.lrc","streamPath":"https://mp4.48.cn/live/c6f4c782-714d-4e27-b6e7-fb6f1dfdc2e5.mp4","screenMode":0},{"liveId":"59ddf7260cf2754cb09226fb","title":"兰昊的直播间","subTitle":"回学校啦","picPath":"/mediasource/live/1507277849737l2lh05xU1z.png","startTime":1507718950365,"memberId":528336,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddf7260cf2754cb09226fb.lrc","streamPath":"https://mp4.48.cn/live/4149e593-d1cf-439c-94aa-483f83bcb733.mp4","screenMode":0},{"liveId":"59ddefbc0cf2754cb09226fa","title":"兰昊的直播间","subTitle":"回学校啦","picPath":"/mediasource/live/1507277849737l2lh05xU1z.png","startTime":1507717052620,"memberId":528336,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddefbc0cf2754cb09226fa.lrc","streamPath":"https://mp4.48.cn/live/cd1d0c02-8b56-421b-a96c-ed721b2ddd5f.mp4","screenMode":0},{"liveId":"59ddeda10cf294bc616de877","title":"陈佳莹的直播间","subTitle":"🌙","picPath":"/mediasource/live/1506348779949sTXEL26XQM.png","startTime":1507716513691,"memberId":530431,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddeda10cf294bc616de877.lrc","streamPath":"https://mp4.48.cn/live/ea7df85e-37ca-4694-81cd-c1ad1724f0f6.mp4","screenMode":0},{"liveId":"59dde6980cf294bc616de875","title":"卢静的直播间","subTitle":"刚下课～聊一会儿\n","picPath":"/mediasource/live/1507714711940L506lJR6b9.jpg","startTime":1507714712097,"memberId":327569,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dde6980cf294bc616de875.lrc","streamPath":"https://mp4.48.cn/live/975c1462-324a-453a-8ca8-94a0bef9a1ab.mp4","screenMode":0},{"liveId":"59dde4a40cf264df95c2a724","title":"左嘉欣的直播间","subTitle":"油","picPath":"/mediasource/live/15056483013606g1w0zu0xI.jpg","startTime":1507714212071,"memberId":327576,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dde4a40cf264df95c2a724.lrc","streamPath":"https://mp4.48.cn/live/19ae0745-8a50-4d86-bc02-3c4ee03b5ee0.mp4","screenMode":0},{"liveId":"59dde28e0cf264df95c2a723","title":"关思雨的电台","subTitle":"嘿嘿嘿嘿嘿嘿","picPath":"/mediasource/live/1507713677712mX2DwPn00o.jpg,/mediasource/live/1507713677924cU4smn15k3.png,/mediasource/live/1507713678044uCZ379CGfY.png","startTime":1507713678071,"memberId":459996,"liveType":2,"picLoopTime":30000,"lrcPath":"/mediasource/live/lrc/59dde28e0cf264df95c2a723.lrc","streamPath":"https://mp4.48.cn/live/2f21bbe4-79fa-4529-8f9b-1786850efb24.mp4","screenMode":0},{"liveId":"59dddb060cf264df95c2a722","title":"潘瑛琪的直播间","subTitle":"(๑´ㅂ`๑)٩( ᐛ )و","picPath":"/mediasource/live/0786db97-8b5a-43e1-b24f-b64e6869614f.jpg","startTime":1507711750638,"memberId":63567,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dddb060cf264df95c2a722.lrc","streamPath":"https://mp4.48.cn/live/47c77d17-5c51-48e4-aa13-e805939f87f1.mp4","screenMode":0},{"liveId":"59ddd7e30cf264df95c2a721","title":"潘瑛琪的直播间","subTitle":"(๑´ㅂ`๑)٩( ᐛ )و","picPath":"/mediasource/live/0786db97-8b5a-43e1-b24f-b64e6869614f.jpg","startTime":1507710947038,"memberId":63567,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddd7e30cf264df95c2a721.lrc","streamPath":"https://mp4.48.cn/live/5e208d55-5da6-4139-b1ce-ffaed8cb799b.mp4","screenMode":0},{"liveId":"59ddcea40cf2255334a09e2e","title":"郑诗琪的直播间","subTitle":"农药","picPath":"/mediasource/live/1507708580562JEvWcaJ9dv.png","startTime":1507708580637,"memberId":530383,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddcea40cf2255334a09e2e.lrc","streamPath":"https://mp4.48.cn/live/9597887d-d517-401d-a5b5-21404f557471.mp4","screenMode":0},{"liveId":"59ddbb970cf2d32740bafa6f","title":"赖梓惜的直播间","subTitle":"搞笑","picPath":"/mediasource/live/1507444473911G26ig8oDzO.png","startTime":1507703703498,"memberId":459995,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddbb970cf2d32740bafa6f.lrc","streamPath":"https://mp4.48.cn/live/11bd98f1-ca9c-4a1c-ae41-d99047941dd4.mp4","screenMode":0},{"liveId":"59ddb9890cf2255334a09e2d","title":"林歆源的直播间","subTitle":"٩(͡๏̯͡๏)۶","picPath":"/mediasource/live/1502638996906qSLpj6IsM8.jpg","startTime":1507703177293,"memberId":541132,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddb9890cf2255334a09e2d.lrc","streamPath":"https://mp4.48.cn/live/d0e575e9-cfb3-40fa-ad4a-7d18a761f087.mp4","screenMode":0},{"liveId":"59dd83620cf2255334a09e2b","title":"许逸的直播间","subTitle":"想播就播：清晨美颜直播电台","picPath":"/mediasource/live/1507685821923sb6q4icnB4.png","startTime":1507689313308,"memberId":399668,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dd83620cf2255334a09e2b.lrc","streamPath":"http://live.us.sinaimg.cn/000FVgFTjx07eV8cZNA4070d010003lF0k01.m3u8","screenMode":0},{"liveId":"59dd82090cf2255334a09e29","title":"许逸的直播间","subTitle":"想播就播：清晨美颜直播电台","picPath":"/mediasource/live/1507685821923sb6q4icnB4.png","startTime":1507688967799,"memberId":399668,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dd82090cf2255334a09e29.lrc","streamPath":"http://live.us.sinaimg.cn/002qwIIfjx07eV6kNqqz070d010000wV0k01.m3u8","screenMode":0},{"liveId":"59dd755a0cf2d32740bafa64","title":"李晴的直播间","subTitle":"，","picPath":"/mediasource/live/1506262804984l8mDqF8ndb.jpg","startTime":1507685722561,"memberId":480668,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dd755a0cf2d32740bafa64.lrc","streamPath":"https://mp4.48.cn/live/da962f55-31bd-40f5-a58b-65b7904457b3.mp4","screenMode":0},{"liveId":"59dcf0a80cf218b4c7fdb4ea","title":"牛聪聪的直播间","subTitle":"我胡汉三！不是 我牛九爷又回来啦！","picPath":"/mediasource/live/14995182322439n7g5O19RQ.jpg","startTime":1507651750165,"memberId":327586,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dcf0a80cf218b4c7fdb4ea.lrc","streamPath":"http://live.us.sinaimg.cn/000COTBUjx07eUut8b8I070d01000cir0k01.m3u8","screenMode":0},{"liveId":"59dcf06c0cf218b4c7fdb4e9","title":"田姝丽的直播间","subTitle":"甜甜的～～❤","picPath":"/mediasource/live/1504004283539e9f5bA52tj.jpg","startTime":1507651690869,"memberId":63571,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dcf06c0cf218b4c7fdb4e9.lrc","streamPath":"http://live.us.sinaimg.cn/0020wDVVjx07eUqTwtzN070d010004hx0k01.m3u8","screenMode":0},{"liveId":"59dcee440cf218b4c7fdb4e7","title":"杨冰怡的直播间","subTitle":"嗷呜？\n","picPath":"/mediasource/live/1506092084172Pi7j3wxx7O.png","startTime":1507651140072,"memberId":6744,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dcee440cf218b4c7fdb4e7.lrc","streamPath":"https://mp4.48.cn/live/dd64558d-875f-49df-94e8-e393ca3216d7.mp4","screenMode":0},{"liveId":"59dcee160cf218b4c7fdb4e6","title":"孙芮的直播间","subTitle":"嘿兄弟！啊啊啊","picPath":"/mediasource/live/1507373031373puiQ3eix3J.jpg","startTime":1507651094081,"memberId":8,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dcee160cf218b4c7fdb4e6.lrc","streamPath":"https://mp4.48.cn/live/33a5863b-f619-49f1-b643-364680f8ccd6.mp4","screenMode":0},{"liveId":"59dce71b0cf218b4c7fdb4e3","title":"青钰雯的直播间","subTitle":"青钰雯的直播间","picPath":"/mediasource/live/1506599356144E99w3Zo6dx.png","startTime":1507649305345,"memberId":327581,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dce71b0cf218b4c7fdb4e3.lrc","streamPath":"http://live.us.sinaimg.cn/000rkjQFjx07eUuuoN43070d01000hWP0k01.m3u8","screenMode":0}],"giftUpdTime":1498211389003,"giftUpdUrl":[],"hasReviewUids":[327587,399662,286979,399668,47,2508,327597,36,327595,327581,327582,327596,6735,6744,40,63,399667,399665,399631,6742,32,26,63574,63579,63562,63571,399672,327592,327585,327568,63567,327567,63551,327571,24,327602,327586,286980,327588,6746,6734,68,49005,38,327565,33,327570,21,327558,63564,63553,35,5564,9073,63558,327601,49006,46,1,327557,327562,410180,5567,327574,6,6739,6747,63561,327572,327576,49,327683,6740,327575,399674,6743,6745,417320,1544,25,63568,327599,14,327591,417321,327569,417333,2470,6738,417311,327580,5,49003,63548,5574,27,399664,39,407119,4,407106,417331,327682,327564,407077,407112,45,410177,63576,327573,327560,63577,11,63572,458335,63563,460003,460002,459995,63581,459989,459994,459993,460005,458358,459992,459996,460004,63559,18,459988,63580,417316,459997,6741,417317,63560,327561,459991,327603,286983,28,8,5973,34,485381,485376,485380,460933,63570,286976,480656,480673,480670,480665,12,524597,20,526172,399654,19,417318,5562,407121,480668,37,67,327563,480675,63582,49007,5566,327577,327566,407104,286977,533852,407071,327579,407110,460007,6432,327598,530392,530384,530385,538735,480672,407132,43,528337,528335,530378,529991,530383,63549,540106,530451,530447,530439,530436,480674,530435,327589,399669,327594,530443,530450,530444,530452,327578,530446,63575,327600,444081,528334,407126,480676,528329,538697,417326,530387,480666,63552,417335,528106,286982,530440,6429,22,3,7,6431,530431,417332,6737,530433,68795,534729,530390,10,407114,480671,528339,541132,530386,530380,286978,63555,63566,63565,530381,63554,417329,528118,407127,63557,480679,593820,528094,530584,410175,528331,417315,407103,592320,417325,528333,407135,419966,417324,480667,459999,407101,407124,594003,594005,16,528336,407168]}
	 */

	private int status;
	private String message;
	private ContentBean content;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ContentBean getContent() {
		return content;
	}

	public void setContent(ContentBean content) {
		this.content = content;
	}

	public static class ContentBean {
		/**
		 * liveList : [{"liveId":"59de13f10cf294bc616de87e","title":"吕梦莹的直播间","subTitle":"日常写作业🐷","picPath":"/mediasource/live/15061690159861h5h22LAZ0.jpg","startTime":1507726321749,"memberId":286982,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59de13f10cf294bc616de87e.lrc","streamPath":"http://2519.liveplay.myqcloud.com/live/2519_3145421.flv","screenMode":0},{"liveId":"59de0c780cf294bc616de87c","title":"陈珂的电台","subTitle":"走近珂学，天气渐渐转凉❤️吃点什么好呢","picPath":"/mediasource/live/15077244071930u8O07aHfs.jpg,/mediasource/live/1507724407319mu25ZhE6HG.jpg,/mediasource/live/1507724407402h0eyfoQM6K.jpg,/mediasource/live/1507724407500xZXPo2KTNc.jpg,/mediasource/live/1507724407617yr1648O022.jpg,/mediasource/live/1507724407713CinPuJQMbq.jpg,/mediasource/live/1507724407818kkQGj03zqP.jpg,/mediasource/live/1507724407988opXFUU1WIu.jpg,/mediasource/live/1507724408112025O19z30i.jpg,/mediasource/live/1507724408308rXJYXXzc8D.jpg","startTime":1507724408463,"memberId":63548,"liveType":2,"picLoopTime":30000,"lrcPath":"/mediasource/live/lrc/59de0c780cf294bc616de87c.lrc","streamPath":"http://play.bcelive.com/live/lss-ge5e1i94x6ziwbnm.flv","screenMode":0},{"liveId":"59de06fd0cf264df95c2a726","title":"乔钰珂的直播间","subTitle":"我来了","picPath":"/mediasource/live/1507723004858b9qbRpO2ZP.jpg","startTime":1507723004983,"memberId":528339,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59de06fd0cf264df95c2a726.lrc","streamPath":"http://2519.liveplay.myqcloud.com/live/2519_3145077.flv","screenMode":0},{"liveId":"59de02d90cf2754cb09226fc","title":"刘力菲的直播间","subTitle":"嘻嘻","picPath":"/mediasource/avatar/327567gFa9N4GB70.jpg","startTime":1507721943866,"memberId":327567,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59de02d90cf2754cb09226fc.lrc","streamPath":"http://pl.live.weibo.com/alicdn/1a94541079112c26d867c0994fe4f5a5_wb480.flv","screenMode":0}]
		 * reviewList : [{"liveId":"59ddfa9b0cf294bc616de879","title":"赵佳蕊的电台","subTitle":"噜噜噜","picPath":"/mediasource/live/1507719835105mVEfM7BOzW.png,/mediasource/live/15066074644142YgM2L6iYX.jpg,/mediasource/live/1506607464545FP48XW2S8x.jpg,/mediasource/live/150660746466600wUiv81Hj.jpg,/mediasource/live/1506607464841KhVZH2oNHf.jpg,/mediasource/live/1506607465055Y4K6MR8y1B.jpg","startTime":1507719835244,"memberId":460005,"liveType":2,"picLoopTime":30000,"lrcPath":"/mediasource/live/lrc/59ddfa9b0cf294bc616de879.lrc","streamPath":"https://mp4.48.cn/live/c6f4c782-714d-4e27-b6e7-fb6f1dfdc2e5.mp4","screenMode":0},{"liveId":"59ddf7260cf2754cb09226fb","title":"兰昊的直播间","subTitle":"回学校啦","picPath":"/mediasource/live/1507277849737l2lh05xU1z.png","startTime":1507718950365,"memberId":528336,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddf7260cf2754cb09226fb.lrc","streamPath":"https://mp4.48.cn/live/4149e593-d1cf-439c-94aa-483f83bcb733.mp4","screenMode":0},{"liveId":"59ddefbc0cf2754cb09226fa","title":"兰昊的直播间","subTitle":"回学校啦","picPath":"/mediasource/live/1507277849737l2lh05xU1z.png","startTime":1507717052620,"memberId":528336,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddefbc0cf2754cb09226fa.lrc","streamPath":"https://mp4.48.cn/live/cd1d0c02-8b56-421b-a96c-ed721b2ddd5f.mp4","screenMode":0},{"liveId":"59ddeda10cf294bc616de877","title":"陈佳莹的直播间","subTitle":"🌙","picPath":"/mediasource/live/1506348779949sTXEL26XQM.png","startTime":1507716513691,"memberId":530431,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddeda10cf294bc616de877.lrc","streamPath":"https://mp4.48.cn/live/ea7df85e-37ca-4694-81cd-c1ad1724f0f6.mp4","screenMode":0},{"liveId":"59dde6980cf294bc616de875","title":"卢静的直播间","subTitle":"刚下课～聊一会儿\n","picPath":"/mediasource/live/1507714711940L506lJR6b9.jpg","startTime":1507714712097,"memberId":327569,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dde6980cf294bc616de875.lrc","streamPath":"https://mp4.48.cn/live/975c1462-324a-453a-8ca8-94a0bef9a1ab.mp4","screenMode":0},{"liveId":"59dde4a40cf264df95c2a724","title":"左嘉欣的直播间","subTitle":"油","picPath":"/mediasource/live/15056483013606g1w0zu0xI.jpg","startTime":1507714212071,"memberId":327576,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dde4a40cf264df95c2a724.lrc","streamPath":"https://mp4.48.cn/live/19ae0745-8a50-4d86-bc02-3c4ee03b5ee0.mp4","screenMode":0},{"liveId":"59dde28e0cf264df95c2a723","title":"关思雨的电台","subTitle":"嘿嘿嘿嘿嘿嘿","picPath":"/mediasource/live/1507713677712mX2DwPn00o.jpg,/mediasource/live/1507713677924cU4smn15k3.png,/mediasource/live/1507713678044uCZ379CGfY.png","startTime":1507713678071,"memberId":459996,"liveType":2,"picLoopTime":30000,"lrcPath":"/mediasource/live/lrc/59dde28e0cf264df95c2a723.lrc","streamPath":"https://mp4.48.cn/live/2f21bbe4-79fa-4529-8f9b-1786850efb24.mp4","screenMode":0},{"liveId":"59dddb060cf264df95c2a722","title":"潘瑛琪的直播间","subTitle":"(๑´ㅂ`๑)٩( ᐛ )و","picPath":"/mediasource/live/0786db97-8b5a-43e1-b24f-b64e6869614f.jpg","startTime":1507711750638,"memberId":63567,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dddb060cf264df95c2a722.lrc","streamPath":"https://mp4.48.cn/live/47c77d17-5c51-48e4-aa13-e805939f87f1.mp4","screenMode":0},{"liveId":"59ddd7e30cf264df95c2a721","title":"潘瑛琪的直播间","subTitle":"(๑´ㅂ`๑)٩( ᐛ )و","picPath":"/mediasource/live/0786db97-8b5a-43e1-b24f-b64e6869614f.jpg","startTime":1507710947038,"memberId":63567,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddd7e30cf264df95c2a721.lrc","streamPath":"https://mp4.48.cn/live/5e208d55-5da6-4139-b1ce-ffaed8cb799b.mp4","screenMode":0},{"liveId":"59ddcea40cf2255334a09e2e","title":"郑诗琪的直播间","subTitle":"农药","picPath":"/mediasource/live/1507708580562JEvWcaJ9dv.png","startTime":1507708580637,"memberId":530383,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddcea40cf2255334a09e2e.lrc","streamPath":"https://mp4.48.cn/live/9597887d-d517-401d-a5b5-21404f557471.mp4","screenMode":0},{"liveId":"59ddbb970cf2d32740bafa6f","title":"赖梓惜的直播间","subTitle":"搞笑","picPath":"/mediasource/live/1507444473911G26ig8oDzO.png","startTime":1507703703498,"memberId":459995,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddbb970cf2d32740bafa6f.lrc","streamPath":"https://mp4.48.cn/live/11bd98f1-ca9c-4a1c-ae41-d99047941dd4.mp4","screenMode":0},{"liveId":"59ddb9890cf2255334a09e2d","title":"林歆源的直播间","subTitle":"٩(͡๏̯͡๏)۶","picPath":"/mediasource/live/1502638996906qSLpj6IsM8.jpg","startTime":1507703177293,"memberId":541132,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59ddb9890cf2255334a09e2d.lrc","streamPath":"https://mp4.48.cn/live/d0e575e9-cfb3-40fa-ad4a-7d18a761f087.mp4","screenMode":0},{"liveId":"59dd83620cf2255334a09e2b","title":"许逸的直播间","subTitle":"想播就播：清晨美颜直播电台","picPath":"/mediasource/live/1507685821923sb6q4icnB4.png","startTime":1507689313308,"memberId":399668,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dd83620cf2255334a09e2b.lrc","streamPath":"http://live.us.sinaimg.cn/000FVgFTjx07eV8cZNA4070d010003lF0k01.m3u8","screenMode":0},{"liveId":"59dd82090cf2255334a09e29","title":"许逸的直播间","subTitle":"想播就播：清晨美颜直播电台","picPath":"/mediasource/live/1507685821923sb6q4icnB4.png","startTime":1507688967799,"memberId":399668,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dd82090cf2255334a09e29.lrc","streamPath":"http://live.us.sinaimg.cn/002qwIIfjx07eV6kNqqz070d010000wV0k01.m3u8","screenMode":0},{"liveId":"59dd755a0cf2d32740bafa64","title":"李晴的直播间","subTitle":"，","picPath":"/mediasource/live/1506262804984l8mDqF8ndb.jpg","startTime":1507685722561,"memberId":480668,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dd755a0cf2d32740bafa64.lrc","streamPath":"https://mp4.48.cn/live/da962f55-31bd-40f5-a58b-65b7904457b3.mp4","screenMode":0},{"liveId":"59dcf0a80cf218b4c7fdb4ea","title":"牛聪聪的直播间","subTitle":"我胡汉三！不是 我牛九爷又回来啦！","picPath":"/mediasource/live/14995182322439n7g5O19RQ.jpg","startTime":1507651750165,"memberId":327586,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dcf0a80cf218b4c7fdb4ea.lrc","streamPath":"http://live.us.sinaimg.cn/000COTBUjx07eUut8b8I070d01000cir0k01.m3u8","screenMode":0},{"liveId":"59dcf06c0cf218b4c7fdb4e9","title":"田姝丽的直播间","subTitle":"甜甜的～～❤","picPath":"/mediasource/live/1504004283539e9f5bA52tj.jpg","startTime":1507651690869,"memberId":63571,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dcf06c0cf218b4c7fdb4e9.lrc","streamPath":"http://live.us.sinaimg.cn/0020wDVVjx07eUqTwtzN070d010004hx0k01.m3u8","screenMode":0},{"liveId":"59dcee440cf218b4c7fdb4e7","title":"杨冰怡的直播间","subTitle":"嗷呜？\n","picPath":"/mediasource/live/1506092084172Pi7j3wxx7O.png","startTime":1507651140072,"memberId":6744,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dcee440cf218b4c7fdb4e7.lrc","streamPath":"https://mp4.48.cn/live/dd64558d-875f-49df-94e8-e393ca3216d7.mp4","screenMode":0},{"liveId":"59dcee160cf218b4c7fdb4e6","title":"孙芮的直播间","subTitle":"嘿兄弟！啊啊啊","picPath":"/mediasource/live/1507373031373puiQ3eix3J.jpg","startTime":1507651094081,"memberId":8,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dcee160cf218b4c7fdb4e6.lrc","streamPath":"https://mp4.48.cn/live/33a5863b-f619-49f1-b643-364680f8ccd6.mp4","screenMode":0},{"liveId":"59dce71b0cf218b4c7fdb4e3","title":"青钰雯的直播间","subTitle":"青钰雯的直播间","picPath":"/mediasource/live/1506599356144E99w3Zo6dx.png","startTime":1507649305345,"memberId":327581,"liveType":1,"picLoopTime":0,"lrcPath":"/mediasource/live/lrc/59dce71b0cf218b4c7fdb4e3.lrc","streamPath":"http://live.us.sinaimg.cn/000rkjQFjx07eUuuoN43070d01000hWP0k01.m3u8","screenMode":0}]
		 * giftUpdTime : 1498211389003
		 * giftUpdUrl : []
		 * hasReviewUids : [327587,399662,286979,399668,47,2508,327597,36,327595,327581,327582,327596,6735,6744,40,63,399667,399665,399631,6742,32,26,63574,63579,63562,63571,399672,327592,327585,327568,63567,327567,63551,327571,24,327602,327586,286980,327588,6746,6734,68,49005,38,327565,33,327570,21,327558,63564,63553,35,5564,9073,63558,327601,49006,46,1,327557,327562,410180,5567,327574,6,6739,6747,63561,327572,327576,49,327683,6740,327575,399674,6743,6745,417320,1544,25,63568,327599,14,327591,417321,327569,417333,2470,6738,417311,327580,5,49003,63548,5574,27,399664,39,407119,4,407106,417331,327682,327564,407077,407112,45,410177,63576,327573,327560,63577,11,63572,458335,63563,460003,460002,459995,63581,459989,459994,459993,460005,458358,459992,459996,460004,63559,18,459988,63580,417316,459997,6741,417317,63560,327561,459991,327603,286983,28,8,5973,34,485381,485376,485380,460933,63570,286976,480656,480673,480670,480665,12,524597,20,526172,399654,19,417318,5562,407121,480668,37,67,327563,480675,63582,49007,5566,327577,327566,407104,286977,533852,407071,327579,407110,460007,6432,327598,530392,530384,530385,538735,480672,407132,43,528337,528335,530378,529991,530383,63549,540106,530451,530447,530439,530436,480674,530435,327589,399669,327594,530443,530450,530444,530452,327578,530446,63575,327600,444081,528334,407126,480676,528329,538697,417326,530387,480666,63552,417335,528106,286982,530440,6429,22,3,7,6431,530431,417332,6737,530433,68795,534729,530390,10,407114,480671,528339,541132,530386,530380,286978,63555,63566,63565,530381,63554,417329,528118,407127,63557,480679,593820,528094,530584,410175,528331,417315,407103,592320,417325,528333,407135,419966,417324,480667,459999,407101,407124,594003,594005,16,528336,407168]
		 */

		private long giftUpdTime;
		@SerializedName("liveList")
		private List<RoomBean> liveList;
		@SerializedName("reviewList")
		private List<RoomBean> reviewList;
		private List<?> giftUpdUrl;
		private List<Integer> hasReviewUids;

		public long getGiftUpdTime() {
			return giftUpdTime;
		}

		public void setGiftUpdTime(long giftUpdTime) {
			this.giftUpdTime = giftUpdTime;
		}

		public List<RoomBean> getLiveList() {
			return liveList;
		}

		public void setLiveList(List<RoomBean> liveList) {
			this.liveList = liveList;
		}

		public List<RoomBean> getReviewList() {
			return reviewList;
		}

		public void setReviewList(List<RoomBean> reviewList) {
			this.reviewList = reviewList;
		}

		public List<?> getGiftUpdUrl() {
			return giftUpdUrl;
		}

		public void setGiftUpdUrl(List<?> giftUpdUrl) {
			this.giftUpdUrl = giftUpdUrl;
		}

		public List<Integer> getHasReviewUids() {
			return hasReviewUids;
		}

		public void setHasReviewUids(List<Integer> hasReviewUids) {
			this.hasReviewUids = hasReviewUids;
		}

		public static class RoomBean {
			/**
			 * liveId : 59de13f10cf294bc616de87e
			 * title : 吕梦莹的直播间
			 * subTitle : 日常写作业🐷
			 * picPath : /mediasource/live/15061690159861h5h22LAZ0.jpg
			 * startTime : 1507726321749
			 * memberId : 286982
			 * liveType : 1
			 * picLoopTime : 0
			 * lrcPath : /mediasource/live/lrc/59de13f10cf294bc616de87e.lrc
			 * streamPath : http://2519.liveplay.myqcloud.com/live/2519_3145421.flv
			 * screenMode : 0
			 */

			private String liveId;
			private String title;
			private String subTitle;
			private String picPath;
			private long startTime;
			private int memberId;
			private int liveType;
			private int picLoopTime;
			private String lrcPath;
			private String streamPath;
			private int screenMode;

			public String getLiveId() {
				return liveId;
			}

			public void setLiveId(String liveId) {
				this.liveId = liveId;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getSubTitle() {
				return subTitle;
			}

			public void setSubTitle(String subTitle) {
				this.subTitle = subTitle;
			}

			public String getPicPath() {
				return picPath;
			}

			public void setPicPath(String picPath) {
				this.picPath = picPath;
			}

			public long getStartTime() {
				return startTime;
			}

			public void setStartTime(long startTime) {
				this.startTime = startTime;
			}

			public int getMemberId() {
				return memberId;
			}

			public void setMemberId(int memberId) {
				this.memberId = memberId;
			}

			public int getLiveType() {
				return liveType;
			}

			public void setLiveType(int liveType) {
				this.liveType = liveType;
			}

			public int getPicLoopTime() {
				return picLoopTime;
			}

			public void setPicLoopTime(int picLoopTime) {
				this.picLoopTime = picLoopTime;
			}

			public String getLrcPath() {
				return lrcPath;
			}

			public void setLrcPath(String lrcPath) {
				this.lrcPath = lrcPath;
			}

			public String getStreamPath() {
				return streamPath;
			}

			public void setStreamPath(String streamPath) {
				this.streamPath = streamPath;
			}

			public int getScreenMode() {
				return screenMode;
			}

			public void setScreenMode(int screenMode) {
				this.screenMode = screenMode;
			}
		}
//
//        public static class LiveListBean extends RoomBean{
//
//        }
//
//        public static class ReviewListBean extends RoomBean{
//
//        }
	}
}
