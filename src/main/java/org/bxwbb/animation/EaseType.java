package org.bxwbb.animation;

public enum EaseType {

    // 基础线性
    /** 匀速线性，无缓动 */
    LINEAR,

    // 二次方缓动
    /** 慢→快，二次入 */
    EASE_IN,
    /** 快→慢，二次出（开关/滑块首选） */
    EASE_OUT,
    /** 慢→快→慢，二次进退 */
    EASE_IN_OUT,

    // 三次方缓动
    /** 三次方加速 */
    CUBIC_IN,
    /** 三次方减速 */
    CUBIC_OUT,
    /** 三次方进退 */
    CUBIC_IN_OUT,

    // 四次方缓动
    /** 四次方强加速 */
    QUART_IN,
    /** 四次方强减速 */
    QUART_OUT,
    /** 四次方强进退 */
    QUART_IN_OUT,

    // 五次方缓动
    /** 五次方极速加速 */
    QUINT_IN,
    /** 五次方极速减速 */
    QUINT_OUT,
    /** 五次方极速进退 */
    QUINT_IN_OUT,

    // 正弦平滑缓动
    /** 正弦慢入 */
    SINE_IN,
    /** 正弦慢出 */
    SINE_OUT,
    /** 正弦平滑进退，柔和自然 */
    SINE_IN_OUT,

    // 回弹后退缓动
    /** 先向后收缩再进入 */
    BACK_IN,
    /** 先向前超出再回弹复位（按钮按压） */
    BACK_OUT,
    /** 两端回弹进退 */
    BACK_IN_OUT,

    // 弹性震动缓动
    /** 弹性入场，轻微拉扯 */
    ELASTIC_IN,
    /** 弹性退场，轻微抖动回弹 */
    ELASTIC_OUT,
    /** 弹性进退 */
    ELASTIC_IN_OUT,

    // 弹跳缓动
    /** 弹跳进入 */
    BOUNCE_IN,
    /** 弹跳结束回落 */
    BOUNCE_OUT,
    /** 弹跳进退全程 */
    BOUNCE_IN_OUT;

    /**
     * 根据原始进度 0~1 计算缓动后进度
     * @param progress 原始归一化进度 0.0 ~ 1.0
     * @return 缓动插值后数值 0.0 ~ 1.0
     */
    @SuppressWarnings("all")
    public float calculate(float p) {
        return switch (this) {
            case LINEAR -> p;

            case EASE_IN -> p * p;
            case EASE_OUT -> 1f - (1f - p) * (1f - p);
            case EASE_IN_OUT -> p < 0.5f
                    ? 2f * p * p
                    : 1f - (float) Math.pow(-2f * p + 2f, 2f) / 2f;

            case CUBIC_IN -> p * p * p;
            case CUBIC_OUT -> 1f - (float) Math.pow(1f - p, 3f);
            case CUBIC_IN_OUT -> p < 0.5f
                    ? 4f * p * p * p
                    : 1f - (float) Math.pow(-2f * p + 2f, 3f) / 2f;

            case QUART_IN -> p * p * p * p;
            case QUART_OUT -> 1f - (float) Math.pow(1f - p, 4f);
            case QUART_IN_OUT -> p < 0.5f
                    ? 8f * p * p * p * p
                    : 1f - (float) Math.pow(-2f * p + 2f, 4f) / 2f;

            case QUINT_IN -> p * p * p * p * p;
            case QUINT_OUT -> 1f - (float) Math.pow(1f - p, 5f);
            case QUINT_IN_OUT -> p < 0.5f
                    ? 16f * p * p * p * p * p
                    : 1f - (float) Math.pow(-2f * p + 2f, 5f) / 2f;

            case SINE_IN -> (float) (1f - Math.cos(p * Math.PI / 2f));
            case SINE_OUT -> (float) Math.sin(p * Math.PI / 2f);
            case SINE_IN_OUT -> (float) (1f - Math.cos(Math.PI * p)) / 2f;

            case BACK_IN -> {
                float c1 = 1.70158f;
                yield p * p * ((c1 + 1f) * p - c1);
            }
            case BACK_OUT -> {
                float c1 = 1.70158f;
                yield (float) ((p - 1f) * (p - 1f) * ((c1 + 1f) * (p - 1f) + c1) + 1f);
            }
            case BACK_IN_OUT -> {
                float c1 = 1.70158f;
                float c2 = c1 * 1.525f;
                yield p < 0.5f
                        ? (float) (Math.pow(2f * p, 2f) * ((c2 + 1f) * 2f * p - c2)) / 2f
                        : (float) (Math.pow(2f * p - 2f, 2f) * ((c2 + 1f) * (2f * p - 2f) + c2) + 2f) / 2f;
            }

            case ELASTIC_IN -> {
                float c4 = (float) (2f * Math.PI) / 3f;
                yield p == 0f ? 0f : p == 1f ? 1f : (float) (-Math.pow(2f, 10f * p - 10f) * Math.sin((p * 10f - 10.75f) * c4));
            }
            case ELASTIC_OUT -> {
                float c4 = (float) (2f * Math.PI) / 3f;
                yield p == 0f ? 0f : p == 1f ? 1f : (float) (Math.pow(2f, -10f * p) * Math.sin((p * 10f - 0.75f) * c4) + 1f);
            }
            case ELASTIC_IN_OUT -> {
                float c5 = (float) (2f * Math.PI) / 4.5f;
                yield p == 0f ? 0f : p == 1f ? 1f : p < 0.5f
                        ? (float) (-Math.pow(2f, 20f * p - 10f) * Math.sin((20f * p - 11.125f) * c5)) / 2f
                        : (float) (Math.pow(2f, -20f * p + 10f) * Math.sin((20f * p - 11.125f) * c5)) / 2f + 1f;
            }

            case BOUNCE_IN -> 1f - bounceOut(1f - p);
            case BOUNCE_OUT -> bounceOut(p);
            case BOUNCE_IN_OUT -> p < 0.5f
                    ? (1f - bounceOut(1f - 2f * p)) / 2f
                    : (1f + bounceOut(2f * p - 1f)) / 2f;
        };
    }

    private float bounceOut(float p) {
        float n1 = 7.5625f;
        float d1 = 2.75f;
        if (p < 1f / d1) {
            return n1 * p * p;
        } else if (p < 2f / d1) {
            p -= 1.5f / d1;
            return n1 * p * p + 0.75f;
        } else if (p < 2.5f / d1) {
            p -= 2.25f / d1;
            return n1 * p * p + 0.9f;
        } else {
            p -= 2.625f / d1;
            return n1 * p * p + 0.98f;
        }
    }
}