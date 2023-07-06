<script setup>

const coverIds = [11, 12, 14, 17, 18, 20, 23, 24, 25, 26, 29]
const coverId = Math.floor(Math.random() * coverIds.length)
const avatarUrl = `https://robohash.org/${coverId}`

</script>

<template>
  <div class="flex-col flex-center">
    <img :src="`https://picsum.photos/id/${coverId}/768/144`">

    <div class="avatar flex-row flex-center">
      <img id="avatar" :src="avatarUrl"/>
    </div>

    <div class="content flex-col flex-center pt-4">
      <div class="flex-row flex-center">
        <div class="name">Mike J.</div>
      </div>
      <div class="username">@username</div>
    </div>

    <div class="flex-row pt-10">
      <div class="flex-col flex-center stats">
        <div class="number">45</div>
        <div>
          <v-icon>mdi-format-list-checks</v-icon>
          All items
        </div>
      </div>

      <div class="flex-col flex-center stats">
        <div class="number">23</div>
        <div>
          <v-icon>mdi-earth</v-icon>
          Public items
        </div>
      </div>

      <div class="flex-col flex-center stats">
        <div class="number">5</div>
        <div>
          <v-icon>mdi-check</v-icon>
          Completed
        </div>
      </div>


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

</style>
