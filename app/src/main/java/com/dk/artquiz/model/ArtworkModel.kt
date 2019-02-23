package com.dk.artquiz.model

/***
 *
 * Generated with JSONToKotlin Plugin.
 */
data class ArtworkModel(
    val _embedded: Embedded,
    val _links: Links,
    val total_count: Any
)

data class Links(
    val next: Next,
    val self: Self
)

data class Self(
    val href: String
)

data class Next(
    val href: String
)

data class Embedded(
    val artworks: List<Artwork>
)

data class Artwork(
    val _embedded: EmbeddedX,
    val _links: LinksX,
    val additional_information: String,
    val blurb: String,
    val can_acquire: Boolean,
    val can_inquire: Boolean,
    val can_share: Boolean,
    val category: String,
    val collecting_institution: String,
    val created_at: String,
    val cultural_maker: Any,
    val date: String,
    val dimensions: Dimensions,
    val exhibition_history: String,
    val iconicity: Double,
    val id: String,
    val image_rights: String,
    val image_versions: List<String>,
    val literature: String,
    val medium: String,
    val provenance: String,
    val published: Boolean,
    val sale_message: Any,
    val series: String,
    val signature: String,
    val slug: String,
    val sold: Boolean,
    val title: String,
    val unique: Boolean?,
    val updated_at: String,
    val website: String
)

data class EmbeddedX(
    val editions: List<Any>
)

data class Dimensions(
    val cm: Cm,
    val `in`: In
)

data class In(
    val depth: Any,
    val diameter: Any,
    val height: Double,
    val text: String,
    val width: Double
)

data class Cm(
    val depth: Any,
    val diameter: Any,
    val height: Double,
    val text: String,
    val width: Double
)

data class LinksX(
    val artists: Artists,
    val collection_users: CollectionUsers,
    val genes: Genes,
    val image: Image,
    val partner: Partner,
    val permalink: Permalink,
    val self: Self,
    val similar_artworks: SimilarArtworks,
    val thumbnail: Thumbnail
)

data class Artists(
    val href: String
)

data class Genes(
    val href: String
)

data class Permalink(
    val href: String
)

data class SimilarArtworks(
    val href: String
)

data class Partner(
    val href: String
)

data class Image(
    val href: String,
    val templated: Boolean
)

data class CollectionUsers(
    val href: String
)

data class Thumbnail(
    val href: String
)