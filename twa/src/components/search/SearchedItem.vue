<script setup>
import {onMounted, ref} from "vue";
import {DEFAULT_IMAGE_URL} from "@/stores/uiStateStore";
import TonLogo from '@/assets/ton_token.png'

const props = defineProps({
  name: String,
  description: String,
  tags: Array[String],
  imageUrl: String,
  url: String,
  price: Number
})

const tags = ref([])

onMounted(() => {
  tags.value = props.tags.slice(0, 3)
})
</script>

<template>
  <div class="item flex-row flex-grow-1">
    <div class="flex flex-column">
      <div class="preview">
        <img
            :src="props.imageUrl || DEFAULT_IMAGE_URL"
            alt="preview">
      </div>
      <div class="flex flex-row flex-center justify-start">
        <div class="font-weight-bold">{{ price }}</div>
        <img class="ton-logo" :src="TonLogo">
      </div>
    </div>
    <div class="details flex-col flex-grow-1">
      <div class="name">{{ props.name }}</div>
      <div class="description">{{ props.description }}</div>
      <div class="tags flex-row">
        <div class="tag" v-for="tag in tags" :key="tag">
          #{{ tag }}
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.item {
  padding: 8px;
  position: relative;
  /*box-sizing: border-box;*/
}

.item:not(:last-child):after {
  content: "";
  display: block;
  position: absolute;
  height: 1px;
  background: #cfcfcf;
  left: 2em;
  right: 2em;
  bottom: 0;
}

.details {
  padding-right: 1em;
}

.name {
  font-weight: bolder;
}

.description {
  font-size: 0.9em;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.tags {
  gap: 8px;
  font-size: 0.9em;
}

.tag {
  padding: 1px 4px;
  background-color: wheat;
  border-radius: 4px;
}

.icon {
  width: 24px;
  height: 24px;
  padding: 2px;
  color: var(--vt-c-black-mute);
}

.preview {
  padding: 0.5em;
}

.preview img {
  max-width: 64px;
  max-height: 64px;
}

.ton-logo {
  width: 1.5em;
  height: 1.5em;
  margin-left: 0.2em;
}

</style>
