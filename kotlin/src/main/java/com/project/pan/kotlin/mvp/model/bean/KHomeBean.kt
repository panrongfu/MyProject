package com.project.pan.kotlin.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * @author: panrongfu
 * @date: 2018/12/21 13:53
 * @describe:
 */
 data class KHomeBean(val dialog: Any,
                      val issueList: ArrayList<Issue>,
                      val newestIssueType: String,
                      val nextPageUrl: String,
                      val nextPublishTime: Long){
    data class Issue(var count: Int,
                     val date: Long,
                     val total:Int,
                     val itemList: ArrayList<Item>,
                     val publishTime: Long,
                     val releaseTime: Long,
                     val type: String,
                     val nextPageUrl:String){
        data class Item(
                val adIndex: Int,
                val `data`: Data,
                val id: Int,
                val tag: Any,
                val type: String
        ):Serializable,MultiItemEntity {
            override fun getItemType(): Int {
                return id
            }
            data class Data(
                    val actionUrl: String,
                    val ad: Boolean,
                    val adTrack: Any,
                    val author: Author,
                    val autoPlay: Boolean,
                    val campaign: Any,
                    val category: String,
                    val collected: Boolean,
                    val consumption: Consumption,
                    val cover: Cover,
                    val dataType: String,
                    val date: Long,
                    val description: String,
                    val descriptionEditor: String,
                    val descriptionPgc: Any,
                    val duration: Long,
                    val favoriteAdTrack: Any,
                    val font: String,
                    val header: Header,
                    val id: Int,
                    val idx: Int,
                    val ifLimitVideo: Boolean,
                    val image: String,
                    val label: Any,
                    val labelList: ArrayList<Any>,
                    val lastViewTime: Any,
                    val library: String,
                    val playInfo: ArrayList<PlayInfo>,
                    val playUrl: String,
                    val played: Boolean,
                    val playlists: Any,
                    val promotion: Any,
                    val provider: Provider,
                    val releaseTime: Long,
                    val remark: Any,
                    val resourceType: String,
                    val searchWeight: Int,
                    val shade: Boolean,
                    val shareAdTrack: Any,
                    val slogan: String,
                    val src: Any,
                    val subtitles: ArrayList<Any>,
                    val tags: ArrayList<Tag>,
                    val text: String,
                    val thumbPlayUrl: Any,
                    val title: String,
                    val titlePgc: Any,
                    val type: String,
                    val waterMarks: Any,
                    val webAdTrack: Any,
                    val webUrl: WebUrl,
                    val itemList:ArrayList<Item>
            ): Serializable{
                data class Consumption(
                        val collectionCount: Int,
                        val replyCount: Int,
                        val shareCount: Int
                )
                data class Cover(
                        val blurred: String,
                        val detail: String,
                        val feed: String,
                        val homepage: String,
                        val sharing: Any
                )
                data class Author(
                        val adTrack: Any,
                        val approvedNotReadyVideoCount: Int,
                        val description: String,
                        val expert: Boolean,
                        val follow: Follow,
                        val icon: String,
                        val id: Int,
                        val ifPgc: Boolean,
                        val latestReleaseTime: Long,
                        val link: String,
                        val name: String,
                        val shield: Shield,
                        val videoNum: Int
                )
                data class Follow(
                        val followed: Boolean,
                        val itemId: Int,
                        val itemType: String
                )
                data class Shield(
                        val itemId: Int,
                        val itemType: String,
                        val shielded: Boolean
                )
                data class WebUrl(
                        val forWeibo: String,
                        val raw: String
                )
                data class PlayInfo(
                        val height: Int,
                        val name: String,
                        val type: String,
                        val url: String,
                        val urlList: ArrayList<Url>,
                        val width: Int
                )
                data class Url(
                        val name: String,
                        val size: Int,
                        val url: String
                )
                data class Tag(
                        val actionUrl: String,
                        val adTrack: Any,
                        val bgPicture: String,
                        val childTagIdList: Any,
                        val childTagList: Any,
                        val communityIndex: Int,
                        val desc: Any,
                        val headerImage: String,
                        val id: Int,
                        val name: String,
                        val tagRecType: String
                )
                data class Provider(
                        val alias: String,
                        val icon: String,
                        val name: String
                )

                data class Header(val id: Int,val icon: String,val iconType: String,val description: String,val title: String,val font: String,val cover: String,val label: Label,
                                  val actionUrl: String ,val subtitle:String, val labelList: ArrayList<Label>): Serializable{
                    data class Label(val text: String,val card: String,val detial: Any,val actionUrl: Any)
                }
            }
        }
    }
}
