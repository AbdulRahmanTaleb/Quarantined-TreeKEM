package com.github.traderjoe95.mls.protocol.types.tree.leaf

import com.github.traderjoe95.mls.codec.Encodable
import com.github.traderjoe95.mls.codec.type.DataType
import com.github.traderjoe95.mls.codec.type.struct.Struct2T
import com.github.traderjoe95.mls.codec.type.struct.lift
import com.github.traderjoe95.mls.codec.type.struct.struct
import com.github.traderjoe95.mls.codec.type.uint64
import com.github.traderjoe95.mls.protocol.types.RefinedBytes.Companion.neqNullable
import com.github.traderjoe95.mls.protocol.types.tree.LeafNode
import java.time.Instant

data class Lifetime(
  val notBefore: ULong,
  val notAfter: ULong,
) : Struct2T.Shape<ULong, ULong>, LeafNodeInfo {
  constructor(notBefore: Instant, notAfter: Instant) : this(
    notBefore.epochSecond.toULong(),
    notAfter.epochSecond.toULong(),
  )

  val notBeforeInstant: Instant
    get() = Instant.ofEpochSecond(notBefore.toLong())
  val notAfterInstant: Instant
    get() = Instant.ofEpochSecond(notAfter.toLong())

  operator fun contains(instant: Instant): Boolean = instant.epochSecond in this

  operator fun contains(epochSeconds: ULong): Boolean = epochSeconds in notBefore..notAfter

  operator fun contains(epochSeconds: Long): Boolean = epochSeconds.toULong() in this

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Lifetime

    if(notAfter != other.notAfter) return false
    if(notBefore != other.notBefore) return false

    return true
  }

  companion object : Encodable<Lifetime> {
    @Suppress("kotlin:S6531", "ktlint:standard:property-naming")
    override val T: DataType<Lifetime> =
      struct("Lifetime") {
        it.field("not_before", uint64.asULong)
          .field("not_after", uint64.asULong)
      }.lift(::Lifetime)

    fun always(): Lifetime {
      return Lifetime(0U, Instant.MAX.epochSecond.toULong())
    }
  }
}
