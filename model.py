from __future__ import annotations

import os
import random
import shutil
from pathlib import Path
from typing import Iterable, List, Sequence, Tuple

import numpy as np

os.environ.setdefault("WANDB_DISABLED", "true")
os.environ.setdefault("WANDB_SILENT", "true")
os.environ.setdefault("TOKENIZERS_PARALLELISM", "false")

PRODUCT_CATEGORY = "Игровая консоль"
BLACKLIST = ["техника", "еда", "одежда", "спорт"]
MODEL_NAME = "sentence-transformers/paraphrase-multilingual-mpnet-base-v2"
OUTPUT_DIR = Path("./finetuned_scorer")
EPOCHS = 1
BATCH_SIZE = 16
DEV_FRACTION = 0.2
TEST_PRODUCTS = [
    "игровая консоль",
    "велосипед",
    "футбольный мяч",
    "джинсовая куртка",
    "шоколад",
    "пылесос робот",
    "йога-коврик",
    "хлеб",
]


def _normalize(text: str) -> str:
    text = text.lower()
    text = __import__("re").sub(r"[^\w ]+", " ", text, flags=__import__("re").UNICODE)
    text = __import__("re").sub(r"\s+", " ", text).strip()
    return text


def build_synthetic_dataset(seed: int = 42) -> List[Tuple[str, str, float]]:
    random.seed(seed)
    buckets = {
        "техника": [
            "игровая консоль",
            "смартфон",
            "ноутбук",
            "планшет",
            "телевизор",
            "умная колонка",
            "холодильник",
            "стиральная машина",
            "пылесос",
            "микроволновка",
            "духовой шкаф",
            "фен",
            "наушники",
            "камерa",
            "монитор",
            "принтер",
            "роутер",
            "геймпад",
            "видеокарта",
            "материнская плата",
            "сервер",
            "смарт-часы",
            "фитнес-браслет",
            "проектор",
            "колонка bluetooth",
            "электросамокат",
            "квадрокоптер",
            "гироскутер",
            "кофемашина",
            "блендер",
            "мультиварка",
            "электрическая зубная щетка",
            "робот-пылесос",
            "саундбар",
            "сетевой накопитель",
            "ssd накопитель",
            "жесткий диск",
            "usb флешка",
            "видеорегистратор",
            "штатив",
            "микрофон",
            "web-камера",
            "принтер 3d",
            "гейминг кресло",
            "игровая клавиатура",
            "игровая мышь",
            "ИБП",
            "электрочайник",
            "обогреватель",
            "кондиционер",
            "утюг",
        ],
        "спорт": [
            "футбольный мяч",
            "баскетбольный мяч",
            "теннисная ракетка",
            "беговые кроссовки",
            "велосипед",
            "шлем вело",
            "скейтборд",
            "самокат трюковой",
            "роликовые коньки",
            "лыжи",
            "сноуборд",
            "гантели",
            "гиря",
            "йога-коврик",
            "эспандер",
            "турник",
            "скакалка",
            "спортивная форма",
            "баскетбольная форма",
            "фитнес-перчатки",
            "боксерские перчатки",
            "боксерская груша",
            "гольф-клюшка",
            "хоккейная клюшка",
            "коньки",
            "защита на колени",
            "защита на локти",
            "жилет утяжелитель",
            "каремат",
            "плавки",
            "купальник",
            "плавательная шапочка",
            "очки для плавания",
            "ласты",
            "маска для дайвинга",
            "неопреновый костюм",
            "трекинговый рюкзак",
            "палки для трекинга",
            "ботинки треккинговые",
            "крепления для снега",
            "палатка",
            "спальник",
            "ковер для кемпинга",
            "горелка туристическая",
            "термос",
            "фляга",
            "манометр велосипедный",
            "насос велосипедный",
            "станок вело",
            "компьютер вело",
            "подседельная сумка",
        ],
        "одежда": [
            "футболка",
            "толстовка",
            "джинсы",
            "брюки",
            "пальто",
            "куртка",
            "пуховик",
            "шапка",
            "шарф",
            "перчатки",
            "кеды",
            "ботинки",
            "туфли",
            "платье",
            "юбка",
            "блузка",
            "жилет",
            "пиджак",
            "костюм",
            "нижнее белье",
            "носки",
            "колготки",
            "сланцы",
            "сандалии",
            "кроссовки",
            "ветровка",
            "джемпер",
            "кардиган",
            "плащ",
            "комбинезон",
            "спортивные штаны",
            "спортивный костюм",
            "термобелье",
            "кепка",
            "панама",
            "ремень",
            "галстук",
            "бейсболка",
            "ботфорты",
            "туфли лодочки",
            "маечка",
            "толстовка худи",
            "леггинсы",
            "балетки",
            "жилетка пуховая",
            "пижама",
            "халат",
            "тапочки",
            "джоггеры",
            "лонгслив",
        ],
        "еда": [
            "яблоки",
            "бананы",
            "груши",
            "картофель",
            "морковь",
            "лук",
            "помидоры",
            "огурцы",
            "курица",
            "говядина",
            "свинина",
            "индейка",
            "рыба",
            "лосось",
            "тунец",
            "хлеб",
            "булка",
            "батон",
            "сыр",
            "творог",
            "молоко",
            "кефир",
            "йогурт",
            "яйца",
            "гречка",
            "рис",
            "макароны",
            "овсянка",
            "шоколад",
            "печенье",
            "конфеты",
            "орехи",
            "семечки",
            "чипсы",
            "сок",
            "вода",
            "минеральная вода",
            "кола",
            "спрайт",
            "энергетик",
            "кофе",
            "чай",
            "масло сливочное",
            "масло подсолнечное",
            "соль",
            "сахар",
            "мёд",
            "кетчуп",
            "майонез",
            "горчица",
            "соевый соус",
        ],
    }

    positives: List[Tuple[str, str, float]] = []
    for bucket, items in buckets.items():
        for item in items:
            positives.append((item, bucket, 1.0))

    negatives: List[Tuple[str, str, float]] = []
    bucket_names = list(buckets.keys())
    for bucket, items in buckets.items():
        other = [b for b in bucket_names if b != bucket]
        for item in items:
            wrong_bucket = random.choice(other)
            negatives.append((item, wrong_bucket, 0.0))

    positives = positives[:200]
    negatives = negatives[:200]
    dataset = positives + negatives
    random.shuffle(dataset)
    return dataset


def split_train_dev(pairs: List[Tuple[str, str, float]], dev_fraction: float = 0.2):
    random.shuffle(pairs)
    cut = max(1, int(len(pairs) * (1 - dev_fraction)))
    return pairs[:cut], pairs[cut:]


def finetune_scorer(
    train_pairs: Sequence[Tuple[str, str, float]],
    model_name: str = MODEL_NAME,
    output_dir: str | Path | None = None,
    epochs: int = 1,
    batch_size: int = 16,
    warmup_steps: int | None = None,
) -> tuple:
    from sentence_transformers import InputExample, SentenceTransformer, losses
    from torch.utils.data import DataLoader

    model = SentenceTransformer(model_name)

    examples = [InputExample(texts=[a, b], label=float(label)) for a, b, label in train_pairs]
    train_dataloader = DataLoader(examples, shuffle=True, batch_size=batch_size)
    train_loss = losses.CosineSimilarityLoss(model)

    if warmup_steps is None:
        warmup_steps = max(1, int(len(train_dataloader) * epochs * 0.1))

    output_dir = Path(output_dir or "./finetuned_scorer")
    model.fit(
        train_objectives=[(train_dataloader, train_loss)],
        epochs=epochs,
        warmup_steps=warmup_steps,
        output_path=str(output_dir),
        show_progress_bar=True,
    )

    return model, str(output_dir)


def select_threshold(
    scores_and_labels: Sequence[Tuple[float, int]],
    metric: str = "f1",
    num_steps: int = 200,
) -> Tuple[float, float]:
    if not scores_and_labels:
        raise ValueError("scores_and_labels must be non-empty")

    scores, labels = zip(*scores_and_labels)
    scores = list(scores)
    labels = list(labels)

    thresholds = np.linspace(0.0, 1.0, num_steps)
    best_thr, best_val = 0.5, -1.0

    for thr in thresholds:
        preds = [1 if s >= thr else 0 for s in scores]
        tp = sum(1 for p, y in zip(preds, labels) if p == 1 and y == 1)
        fp = sum(1 for p, y in zip(preds, labels) if p == 1 and y == 0)
        fn = sum(1 for p, y in zip(preds, labels) if p == 0 and y == 1)

        precision = tp / (tp + fp + 1e-9)
        recall = tp / (tp + fn + 1e-9)
        f1 = 2 * precision * recall / (precision + recall + 1e-9)

        value = {"f1": f1, "precision": precision, "recall": recall}.get(metric, f1)
        if value > best_val:
            best_val, best_thr = value, thr

    return best_thr, best_val


MIN_THRESHOLD = 0.55


def archive_model(model_dir: Path) -> Path:
    """Zip the fine-tuned model directory for download (Colab-friendly)."""

    base_name = model_dir.as_posix()
    archive_path = shutil.make_archive(base_name, "zip", root_dir=model_dir)
    return Path(archive_path)


class CategoryBlacklistScorer:
    def __init__(self, model_name: str | None = MODEL_NAME, model=None):
        self.model_name = model_name
        self._model = model

    def _ensure_model(self):
        from sentence_transformers import SentenceTransformer

        if self._model is None:
            self._model = SentenceTransformer(self.model_name)
        return self._model

    def _embed(self, texts: Sequence[str]):
        model = self._ensure_model()
        return np.asarray(model.encode(list(texts), convert_to_numpy=True, normalize_embeddings=True))

    def score(self, product_category: str, blacklist_categories: Iterable[str], top_k: int | None = None):
        blacklist = list(blacklist_categories)
        if not product_category:
            raise ValueError("product_category must be non-empty")
        if not blacklist:
            return []

        queries = [_normalize(product_category)]
        candidates = [_normalize(item) for item in blacklist]

        query_vec = self._embed(queries)[0]
        candidate_vecs = self._embed(candidates)
        scores = candidate_vecs @ query_vec

        pairs = sorted(zip(blacklist, scores.tolist()), key=lambda x: x[1], reverse=True)
        if top_k is not None:
            pairs = pairs[: max(1, top_k)]
        return pairs


def main():
    print("Генерация синтетического датасета...")
    dataset = build_synthetic_dataset()
    train_pairs, dev_pairs = split_train_dev(dataset, DEV_FRACTION)
    print(f"Тренировочных пар: {len(train_pairs)}, валидационных пар: {len(dev_pairs)}")

    print("\nТонкая настройка модели...")
    model, finetuned_dir = finetune_scorer(
        train_pairs,
        output_dir=OUTPUT_DIR,
        epochs=EPOCHS,
        batch_size=BATCH_SIZE,
    )
    print(f"Модель сохранена в: {finetuned_dir}")

    scorer = CategoryBlacklistScorer(model=model)

    print("\nОценка на валидации и подбор порога...")
    scores_and_labels = []
    for pc, bl, lbl in dev_pairs:
        score = dict(scorer.score(pc, [bl]))[bl]
        scores_and_labels.append((score, int(lbl)))

    thr, best = select_threshold(scores_and_labels, metric="f1")
    if thr < MIN_THRESHOLD:
        thr = MIN_THRESHOLD
    print(f"Лучший порог (F1, не ниже {MIN_THRESHOLD}): {thr:.3f}, F1={best:.3f}")

    print("\nПримеры скор после дообучения:")
    for name, score in scorer.score(PRODUCT_CATEGORY, BLACKLIST, top_k=None):
        flag = "<-- блокировать" if score >= thr else ""
        print(f"  {name:10s} -> {score:.3f} {flag}")

    print("\nПакетная проверка примеров товаров:")
    for prod in TEST_PRODUCTS:
        scored = scorer.score(prod, BLACKLIST, top_k=None)
        top_name, top_score = scored[0]
        decision = "БЛОК" if top_score >= thr else "РАЗРЕШИТЬ"
        print(f"- {prod:20s} -> {top_name:8s} score={top_score:.3f} [{decision}]")

    print("\nАрхивация дообученной модели...")
    archive = archive_model(Path(finetuned_dir))
    print(f"Архив модели готов: {archive}")


if __name__ == "__main__":
    main()
