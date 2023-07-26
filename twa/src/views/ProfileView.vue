<script setup>
import {useWishlistStore} from "@/stores/wishlistStore"
import TonLogo from '@/assets/ton_token.png'

const store = useWishlistStore()

const coverIds = [11, 12, 14, 17, 18, 20, 23, 24, 25, 26, 29]
const coverId = Math.floor(Math.random() * coverIds.length)

const {firstName, lastName, username, imageUrl} = store.profile
const {allItemsCount, publicCount, doneCount} = store.profileStats

let name = firstName
if (lastName && lastName.length > 0) {
  name += ` ${lastName.slice(0, 1).toUpperCase()}.`
}

</script>

<template>
  <div class="flex-col flex-center">
    <img :src="`https://picsum.photos/id/${coverId}/768/144`">

    <div class="avatar flex-row flex-center">
      <img id="avatar" :src="imageUrl"/>
    </div>

    <div class="content flex-col flex-center pt-4">
      <div class="flex-row flex-center">
        <div class="name">{{ name }}</div>
      </div>
      <div class="username" v-if="username">
        @{{ username }}
      </div>
    </div>

    <div class="flex-row pt-10">
      <div class="flex-col flex-center stats">
        <div class="number">{{allItemsCount}}</div>
        <v-icon>mdi-format-list-checks</v-icon>
        <div>
          Total
        </div>
      </div>

      <div class="flex-col flex-center stats">
        <div class="number">{{publicCount}}</div>
        <v-icon>mdi-earth</v-icon>
        <div>
          Public
        </div>
      </div>

      <div class="flex-col flex-center stats">
        <div class="number">{{doneCount}}</div>
        <v-icon>mdi-check</v-icon>
        <div>
          Completed
        </div>
      </div>

    </div>

    <div class="mt-6">
      <v-btn>
        <template v-slot:prepend>
          <img class="ton-logo" :src="TonLogo">
        </template>
        Connect TON Wallet
      </v-btn>
    </div>

  </div>

</template>

<style scoped>

.avatar {
  background-color: white;
  transform: translateY(-50%);
  border-radius: 50%;
  padding: 2px;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
}

.avatar img {
  width: 128px;
  border-radius: 50%;
}

.content {
  margin-top: -64px;
}

.name {
  font-weight: bold;
}

.username {
  font-size: 0.8em;
  color: #2c3e50;
}

.stats {
  padding-left: 2em;
  padding-right: 2em;
}

.number {
  font-size: 2em;
  font-weight: bold;
}

.ton-logo {
  width: 1.5em;
  height: 1.5em;
}


</style>
