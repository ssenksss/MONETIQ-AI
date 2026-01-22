<template>
  <SectionWrapper class="tier-page">
    <h1 class="premium-title">Monetization Plan</h1>

    <div class="document-container">
      <div class="plan-box">
        <p v-if="!premiumPlan.length" class="generating">
          Generating your monetization plan<span class="dots">{{ dots }}</span>
        </p>

        <div v-else class="plan-content">
          <p class="plan-intro">
            Unlock daily actionable tips to grow your Instagram and monetize your content.
          </p>

          <ul class="plan-list">
            <li v-for="task in premiumPlan" :key="task.day">
              <strong>Day {{ task.day }}:</strong> {{ task.text }}
            </li>
          </ul>
        </div>
      </div>

      <div class="document-controls" v-if="premiumPlan.length">
        <button @click="downloadPlan" class="download-btn">Download PDF</button>
        <button @click="downloadPlanWord" class="download-btn">Download Word</button>
      </div>
    </div>
  </SectionWrapper>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, onUnmounted } from 'vue'
import { useUserStore, PremiumTask } from '@/stores/userStore'
import SectionWrapper from '@/components/layout/SectionWrapper.vue'

import jsPDF from 'jspdf'
import 'jspdf-autotable'

import { Document, Packer, Paragraph, TextRun } from "docx"
import { saveAs } from "file-saver"

const userStore = useUserStore()
const premiumPlan = computed<PremiumTask[]>(() => userStore.plan?.items || [])

const dots = ref('')
let interval: ReturnType<typeof setInterval> | null = null

onMounted(() => {
  let count = 0
  interval = setInterval(() => {
    count = (count + 1) % 4
    dots.value = '.'.repeat(count)
  }, 500)

  if (userStore.username) {
    userStore.fetchGeneratedPlan(userStore.username)
  } else {
    userStore.fetchMe().then(() => {
      if (userStore.username) userStore.fetchGeneratedPlan(userStore.username)
    })
  }
})

onUnmounted(() => {
  if (interval) clearInterval(interval)
})

const downloadPlan = () => {
  const doc = new jsPDF()
  doc.setFontSize(22)
  doc.text('Premium 30-Day Monetization Plan', 14, 20)
  doc.setFontSize(12)
  doc.text('Unlock daily actionable tips to grow your Instagram and monetize your content.', 14, 30)

  const tableData = premiumPlan.value.map(task => [task.day, task.text])
  ;(doc as any).autoTable({
    startY: 40,
    head: [['Day', 'Task']],
    body: tableData,
    styles: { fontSize: 11 },
    headStyles: { fillColor: [54, 162, 235] }
  })

  doc.save('Monetization-Plan.pdf')
}

const downloadPlanWord = async () => {
  const doc = new Document({
    sections: [{
      children: [
        new Paragraph({ children: [new TextRun({ text: "Premium 30-Day Monetization Plan", bold: true, size: 28 })] }),
        new Paragraph({ children: [new TextRun({ text: "Unlock daily actionable tips to grow your Instagram and monetize your content.", size: 24 })] }),
        ...premiumPlan.value.map(task =>
            new Paragraph({
              children: [
                new TextRun({ text: `Day ${task.day}: `, bold: true }),
                new TextRun({ text: task.text })
              ]
            })
        )
      ]
    }]
  })

  const blob = await Packer.toBlob(doc)
  saveAs(blob, "Monetization-Plan.docx")
}
</script>

<style scoped lang="scss">
@import '@/assets/styles/variables';
@import '@/assets/styles/mixins';

.premium-title {
  font-family: $font-heading;
  font-weight: 700;
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: $space-lg;
  background: linear-gradient(70deg, $primary 0%, $primary 40%, $secondary 70%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.document-container {
  display: flex;
  gap: 2rem;

  .plan-box {
    flex: 1;
    max-height: 600px;
    overflow-y: auto;
    padding: $space-lg;
    border: 1px solid #e0e0e0;
    border-radius: $radius-lg;
    background-color: rgba(255,255,255,0.05);
    box-shadow: 0 4px 12px rgba(0,0,0,0.05);
  }

  .generating {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    text-align: center;
    font-size: 1rem;
    line-height: 1.5;

    .dots {
      display: inline-block;
      width: 2rem;
      animation: blink 1s infinite steps(3, end);
    }

    @keyframes blink {
      0% { content: "."; }
      33% { content: ".."; }
      66% { content: "..."; }
      100% { content: ""; }
    }
  }

  .plan-content .plan-intro {
    font-family: $font-heading;
    font-weight: 200;
    color: $text-muted;
    margin-bottom: $space-md;
    font-size: 1rem;
  }

  .plan-list {
    list-style: none;
    padding: 0;

    li {
      font-family: $font-heading;
      font-weight: 200;
      color: $text-muted;
      padding: $space-sm 0;
      border-bottom: 1px solid #e0e0e0;

      &:last-child {
        border-bottom: none;
      }
    }
  }

  .document-controls {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    align-self: flex-start;

    .download-btn {
      padding: 0.75rem 1.5rem;
      border-radius: $radius-md;
      border: none;
      font-weight: 600;
      background: linear-gradient(90deg, $primary, $secondary);
      color: white;
      cursor: pointer;
      transition: $transition;

      &:hover {
        opacity: 0.9;
        transform: scale(1.03);
      }
    }
  }
}
</style>
